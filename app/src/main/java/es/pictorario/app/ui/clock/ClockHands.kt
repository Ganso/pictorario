package es.pictorario.app.ui.clock

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import es.pictorario.app.domain.BoardType
import es.pictorario.app.domain.ClockGeometry
import es.pictorario.app.domain.Sequence
import es.pictorario.app.domain.Settings
import es.pictorario.app.domain.TimeIndicator
import es.pictorario.app.domain.Point as ClockPoint
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.cos
import kotlin.math.sin

/**
 * Ticks the wall clock, and only while the screen is actually in front.
 *
 * The B4A version started a 500 ms timer in `Activity_Create` and never stopped
 * it (`Visualizacion.bas:79`), so it kept firing in the background. Tying the
 * loop to [repeatOnLifecycle] fixes that without any lifecycle code of our own.
 *
 * @param needsSeconds whether anything on screen changes faster than once a
 *   minute. When nothing does, the loop sleeps until the next minute instead of
 *   waking twice a second all day.
 */
@Composable
fun rememberClockTick(needsSeconds: Boolean): LocalTime {
    val owner = LocalLifecycleOwner.current
    val time by produceState(LocalTime.now(), owner, needsSeconds) {
        owner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            while (true) {
                val now = LocalTime.now()
                value = now
                delay(
                    if (needsSeconds) FAST_TICK_MILLIS
                    else (60L - now.second) * 1_000L - now.nano / 1_000_000L,
                )
            }
        }
    }
    return time
}

private const val FAST_TICK_MILLIS = 500L

/**
 * The moving half of the clock, drawn over [ClockBoard] so only this layer is
 * repainted on each tick.
 */
@Composable
fun ClockHands(
    sequence: Sequence,
    settings: Settings,
    now: LocalTime,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier) {
        val geometry = geometryFor(sequence, size.width)
        drawHands(sequence, settings, geometry, now)
        // The centre pin is drawn whatever the time, exactly as in the original.
        drawCircle(Color.Black, radius = geometry.radius * 0.1f, center = geometry.center.toOffset())
    }
}

private fun DrawScope.drawHands(
    sequence: Sequence,
    settings: Settings,
    geometry: ClockGeometry,
    now: LocalTime,
) {
    val board = sequence.board
    if (board.timeIndicator == TimeIndicator.NONE) return

    // Outside the half of the day this board covers, the current time has no
    // place on the dial, so nothing is drawn.
    val hands = ClockGeometry.handHours(board.type, sequence.activities)
    if (now.hour < hands.min || now.hour > hands.max) return

    val center = geometry.center.toOffset()
    val hour = now.hour.toFloat()
    val minute = now.minute.toFloat()

    fun hand(target: Offset, color: Long, width: Dp) = drawLine(
        color = Color(color.toInt()),
        start = center,
        end = target,
        strokeWidth = width.toPx(),
        cap = StrokeCap.Round,
    )

    // An arc board, or a board that only spells out the hour, gets a single
    // thick pointer instead of a hand set.
    if (board.type == BoardType.FULL_SEQUENCE || board.timeIndicator == TimeIndicator.HOUR) {
        hand(geometry.pointAt(hour, minute, 0.8f).toOffset(), settings.secondColor, 8.dp)
        return
    }

    // The original stretched the hands into an ellipse by using different
    // fractions for x and y (0.7/0.6 for the hour hand, 0.8/0.75 for the
    // minute). Corrected to a single fraction each, so they stay circular.
    hand(geometry.pointAt(hour, minute, 0.7f).toOffset(), settings.hourColor, 8.dp)
    hand(radial(geometry, 270f + now.minute * 6f, 0.8f), settings.minuteColor, 6.dp)

    if (board.timeIndicator == TimeIndicator.HOUR_MINUTE_SECOND) {
        hand(radial(geometry, 270f + now.second * 6f, 0.9f), settings.secondColor, 4.dp)
    }
}

/**
 * Minute and second hands sweep the dial evenly whatever the board type, so
 * they are placed by angle rather than through the hour mapping.
 */
private fun radial(geometry: ClockGeometry, degrees: Float, distance: Float): Offset {
    val radians = Math.toRadians(degrees.toDouble())
    return Offset(
        (cos(radians) * geometry.radius * distance + geometry.center.x).toFloat(),
        (sin(radians) * geometry.radius * distance + geometry.center.y).toFloat(),
    )
}

private fun ClockPoint.toOffset() = Offset(x, y)
