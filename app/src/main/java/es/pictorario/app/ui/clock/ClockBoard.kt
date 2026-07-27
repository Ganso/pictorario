package es.pictorario.app.ui.clock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.domain.BoardType
import es.pictorario.app.domain.ClockGeometry
import es.pictorario.app.domain.Palette
import es.pictorario.app.domain.SECTOR_RADIUS_FRACTION
import es.pictorario.app.domain.Sequence
import es.pictorario.app.domain.TimeFormat
import es.pictorario.app.domain.Point as ClockPoint
import kotlin.math.cos
import kotlin.math.sin

/** Panel proportions from `LS_visualizarsecuencia.java`: 130% of the width. */
const val BOARD_ASPECT_RATIO = 1f / 1.3f

/** Backdrop of the visualisation screen, the `0xFFF0FFFF` of the original. */
val BoardBackground = Color(0xFFF0FFFF)

private val FrameColor = Color(0xFF808080)
private val FaceColor = Color.White
private val TickColor = Color(0xFFCCCCCC)
private val NumberColor = Color(0xFF444444)

/**
 * The static half of the clock: frame, face, hour marks and numbers, and one
 * coloured sector per activity. Redrawn only when the sequence, the settings or
 * the size change — the hands live in a separate layer on top.
 */
@Composable
fun ClockBoard(
    sequence: Sequence,
    format24h: Boolean,
    screenHeightPx: Float,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
) {
    val measurer = rememberTextMeasurer()

    Canvas(modifier.fillMaxWidth()) {
        val geometry = geometryFor(sequence, size.width)

        drawFace(sequence.board.type, geometry, screenHeightPx)
        drawHourMarks(geometry, sequence, format24h, measurer)
        drawSectors(sequence, geometry, selectedIndex)
    }
}

/** Builds the geometry for a dial drawn [width] pixels wide. */
fun geometryFor(sequence: Sequence, width: Float): ClockGeometry = ClockGeometry(
    boardType = sequence.board.type,
    hours = ClockGeometry.dialHours(sequence.board.type, sequence.activities),
    // Centre and radius are measured against the WIDTH, never the height:
    // reading them off the height would stretch the dial on a tablet.
    center = ClockPoint(width * 0.50f, width * 0.60f),
    radius = width * 0.45f,
)

private fun DrawScope.drawFace(
    boardType: BoardType,
    geometry: ClockGeometry,
    screenHeightPx: Float,
) {
    val center = geometry.center.toOffset()

    if (boardType != BoardType.FULL_SEQUENCE) {
        drawCircle(FrameColor, radius = geometry.radius * 1.05f, center = center)
        drawCircle(FaceColor, radius = geometry.radius, center = center)
        return
    }

    // The arc board is not a plain 300° wedge: the original clipped a full
    // circle with a polygon, and the shape of that polygon is what gives the
    // board its horseshoe outline. Reproduced vertex for vertex.
    clipPath(notchPath(geometry, screenHeightPx, apexOffsetY = screenHeightPx * 0.03f, 114f, 81f)) {
        drawCircle(FrameColor, radius = geometry.radius * 1.05f, center = center)
    }
    clipPath(notchPath(geometry, screenHeightPx, apexOffsetY = 0f, 116f, 80f)) {
        drawCircle(FaceColor, radius = geometry.radius, center = center)
    }
}

/**
 * Everything except the wedge cut out of the bottom of the dial.
 *
 * The closing vertex is not a polar point: `Visualizacion.bas:132` and `:143`
 * scale its x by three radii but its y by only one, so the right-hand edge of
 * the notch leans out much further than a radial cut would. Taking it for a
 * point at [rightDegrees] and one radius — the obvious reading — pulls that edge
 * far too close to the centre line and visibly changes the horseshoe.
 */
private fun DrawScope.notchPath(
    geometry: ClockGeometry,
    screenHeightPx: Float,
    apexOffsetY: Float,
    leftDegrees: Float,
    rightDegrees: Float,
): Path {
    val (cx, cy) = geometry.center.let { it.x to it.y }
    val r = geometry.radius
    val far = maxOf(size.width, size.height, screenHeightPx)

    fun at(degrees: Float, xDistance: Float, yDistance: Float): Offset {
        val radians = Math.toRadians(degrees.toDouble())
        return Offset(
            (cos(radians) * xDistance + cx).toFloat(),
            (sin(radians) * yDistance + cy).toFloat(),
        )
    }

    return Path().apply {
        moveTo(cx, cy + apexOffsetY)
        at(leftDegrees, r * 3f, r * 3f).let { lineTo(it.x, it.y) }
        lineTo(0f, far)
        lineTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, far)
        at(rightDegrees, r * 3f, r).let { lineTo(it.x, it.y) }
        close()
    }
}

private fun DrawScope.drawHourMarks(
    geometry: ClockGeometry,
    sequence: Sequence,
    format24h: Boolean,
    measurer: TextMeasurer,
) {
    val style = TextStyle(color = NumberColor, fontSize = 15.sp)

    for (hour in geometry.hours.min..geometry.hours.max) {
        val tick = geometry.pointAt(hour.toFloat(), 0f, 0.95f).toOffset()
        drawCircle(TickColor, radius = geometry.radius * 0.02f, center = tick)

        val label = TimeFormat.displayHour(hour.mod(24), format24h).toString()
        val layout = measurer.measure(label, style)
        val at = geometry.pointAt(hour.toFloat(), 0f, 0.85f).toOffset()
        drawText(
            textLayoutResult = layout,
            topLeft = Offset(
                at.x - layout.size.width / 2f,
                at.y - layout.size.height / 2f,
            ),
        )
    }
}

private fun DrawScope.drawSectors(
    sequence: Sequence,
    geometry: ClockGeometry,
    selectedIndex: Int,
) {
    val sectorRadius = geometry.radius * SECTOR_RADIUS_FRACTION
    val topLeft = Offset(
        geometry.center.x - sectorRadius,
        geometry.center.y - sectorRadius,
    )
    val diameter = Size(sectorRadius * 2, sectorRadius * 2)

    ClockGeometry.visibleActivities(sequence.board.type, sequence.activities)
        .forEach { (index, activity) ->
            val start = geometry.startAngle(activity)
            val sweep = geometry.sweepAngle(activity)
            drawArc(
                color = Color(Palette.color(index).toInt()),
                startAngle = start,
                sweepAngle = sweep,
                useCenter = true,
                topLeft = topLeft,
                size = diameter,
            )
            if (index == selectedIndex) {
                // Only the outer edge is outlined, not the two radii: the
                // original stroked a full circle and let the wedge clip it, so
                // the straight sides never showed.
                drawArc(
                    color = Color.Red,
                    startAngle = start,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = diameter,
                    style = Stroke(width = SelectedSectorStroke.toPx()),
                )
            }
        }
}

private fun ClockPoint.toOffset() = Offset(x, y)

/** Thickness the original used for the selected sector's outline. */
val SelectedSectorStroke = 5.dp
