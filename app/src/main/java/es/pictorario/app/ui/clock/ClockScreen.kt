package es.pictorario.app.ui.clock

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.R
import es.pictorario.app.domain.BoardType
import es.pictorario.app.domain.ClockGeometry
import es.pictorario.app.domain.Point
import es.pictorario.app.domain.Sequence
import es.pictorario.app.domain.TimeFormat
import es.pictorario.app.domain.TimeIndicator
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.common.PictogramImage
import es.pictorario.app.ui.common.lockGesture
import java.time.LocalTime

/**
 * The visualisation screen: the dial, its hands, a pictogram button per
 * activity and the carousel underneath. This is the screen the child actually
 * uses, and the one the alarm opens.
 */
@Composable
fun ClockScreen(state: PictorarioState, sequenceIndex: Int) {
    val sequence = state.sequences.getOrNull(sequenceIndex) ?: return
    val settings = state.settings
    val screenHeightPx = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }

    val showsSeconds = sequence.board.timeIndicator == TimeIndicator.HOUR_MINUTE_SECOND
    val now = rememberClockTick(needsSeconds = showsSeconds)

    var selected by remember(sequenceIndex) { mutableIntStateOf(-1) }

    // Whatever is happening right now is selected on arrival, and again as the
    // day moves on, unless the user has picked something else in the meantime.
    LaunchedEffect(now.hour, now.minute, sequence.activities) {
        val minutes = now.hour * 60 + now.minute
        val current = sequence.activities.indexOfFirst { it.contains(minutes) }
        if (current >= 0 && selected == -1) selected = current
    }

    BackHandler(enabled = !settings.appProtected) { state.navigateHome() }

    Column(
        modifier = Modifier.fillMaxSize().background(BoardBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DialArea(
            sequence = sequence,
            state = state,
            sequenceIndex = sequenceIndex,
            now = now,
            selected = selected,
            screenHeightPx = screenHeightPx,
            onSelect = { selected = it },
        )

        if (selected in sequence.activities.indices) {
            ActivityPager(
                sequence = sequence,
                selectedIndex = selected,
                now = now,
                format24h = settings.format24h,
                repository = state.pictograms,
                onSelect = { selected = it },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun DialArea(
    sequence: Sequence,
    state: PictorarioState,
    sequenceIndex: Int,
    now: LocalTime,
    selected: Int,
    screenHeightPx: Float,
    onSelect: (Int) -> Unit,
) {
    val settings = state.settings
    val density = LocalDensity.current
    var dialWidthPx by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    val geometry = remember(sequence, dialWidthPx) {
        if (dialWidthPx > 0f) geometryFor(sequence, dialWidthPx) else null
    }

    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(BOARD_ASPECT_RATIO)
            .pointerInput(geometry, sequence.activities) {
                val geom = geometry ?: return@pointerInput
                val visible = ClockGeometry.visibleActivities(
                    sequence.board.type,
                    sequence.activities,
                )
                detectTapGestures { offset ->
                    val hit = geom.hitTest(Point(offset.x, offset.y), visible.map { it.value })
                    if (hit != null) onSelect(visible[hit].index)
                }
            },
    ) {
        ClockBoard(
            sequence = sequence,
            format24h = settings.format24h,
            screenHeightPx = screenHeightPx,
            selectedIndex = selected,
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { dialWidthPx = it.width.toFloat() },
        )
        ClockHands(
            sequence = sequence,
            settings = settings,
            now = now,
            modifier = Modifier.fillMaxSize(),
        )

        if (sequence.board.timeIndicator != TimeIndicator.NONE) {
            Text(
                text = TimeFormat.digitalClock(now.hour, now.minute, settings.format24h),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = DigitalClockColor,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 10.dp),
            )
        }

        geometry?.let { geom ->
            PictogramButtons(sequence, geom, state, density, onSelect)
            CentrePictogram(sequence, geom, state, selected, density)
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (!settings.appProtected) {
                Button(
                    onClick = state::navigateHome,
                    modifier = Modifier.weight(1f).height(60.dp),
                ) {
                    Text("Cerrar visualización", fontSize = 18.sp)
                }
            } else {
                Box(Modifier.weight(1f))
            }

            val protectedModifier = if (settings.appProtected) {
                Modifier.lockGesture(state::navigateHome)
            } else {
                Modifier.clickable { state.cycleBoardType(sequenceIndex) }
            }
            Image(
                painter = painterResource(boardIcon(sequence.board.type, settings.appProtected)),
                contentDescription = if (settings.appProtected) "Desbloquear" else "Cambiar vista",
                modifier = Modifier.size(60.dp).then(protectedModifier),
            )
        }
    }
}

/** One tappable pictogram per activity, laid out around the dial. */
@Composable
private fun PictogramButtons(
    sequence: Sequence,
    geometry: ClockGeometry,
    state: PictorarioState,
    density: androidx.compose.ui.unit.Density,
    onSelect: (Int) -> Unit,
) {
    val percent = sequence.board.iconSizePercent
    if (percent <= 0) return

    val sizePx = geometry.radius / 0.45f * percent / 100f
    val sizeDp = with(density) { sizePx.toDp() }

    ClockGeometry.visibleActivities(sequence.board.type, sequence.activities)
        .forEachIndexed { position, (index, activity) ->
            val centre = geometry.buttonCenter(activity, position)
            PictogramAt(
                pictogramId = activity.pictogramId,
                description = activity.description,
                centre = centre,
                size = sizeDp,
                density = density,
                repository = state.pictograms,
                modifier = Modifier.clickable { onSelect(index) },
            )
        }
}

/** The 80 dp pictogram of the selected activity, pinned to the middle of the dial. */
@Composable
private fun CentrePictogram(
    sequence: Sequence,
    geometry: ClockGeometry,
    state: PictorarioState,
    selected: Int,
    density: androidx.compose.ui.unit.Density,
) {
    val activity = sequence.activities.getOrNull(selected) ?: return
    PictogramAt(
        pictogramId = activity.pictogramId,
        description = activity.description,
        centre = geometry.center,
        size = 80.dp,
        density = density,
        repository = state.pictograms,
        background = Color.White,
    )
}

@Composable
private fun PictogramAt(
    pictogramId: Int,
    description: String,
    centre: Point,
    size: Dp,
    density: androidx.compose.ui.unit.Density,
    repository: es.pictorario.app.data.PictogramRepository,
    modifier: Modifier = Modifier,
    background: Color = Color.Transparent,
) {
    val half = with(density) { size.toPx() / 2f }
    Box(
        modifier = Modifier
            .offset {
                androidx.compose.ui.unit.IntOffset(
                    (centre.x - half).toInt(),
                    (centre.y - half).toInt(),
                )
            }
            .size(size)
            .background(background)
            .then(modifier),
    ) {
        PictogramImage(
            pictogramId = pictogramId,
            repository = repository,
            size = size,
            contentDescription = description,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

/** The `CambiarVista` button shows which board is currently on screen. */
private fun boardIcon(boardType: BoardType, appProtected: Boolean): Int = when {
    appProtected -> R.drawable.candado
    boardType == BoardType.MORNING_12H -> R.drawable.manana
    boardType == BoardType.AFTERNOON_12H -> R.drawable.tarde
    boardType == BoardType.DAY_24H -> R.drawable.dia
    else -> R.drawable.fila
}

private val DigitalClockColor = Color(0xFF909090)
