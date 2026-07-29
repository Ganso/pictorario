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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
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
import es.pictorario.app.domain.BOARD_TYPE_LABELS
import es.pictorario.app.ui.common.Help
import es.pictorario.app.ui.common.Notice
import es.pictorario.app.ui.common.NoticeController
import es.pictorario.app.ui.common.PictogramImage
import es.pictorario.app.ui.common.rememberNotice
import es.pictorario.app.ui.home.UnlockPadlock
import es.pictorario.app.ui.common.isLandscape
import es.pictorario.app.ui.common.readableWidth
import es.pictorario.app.ui.common.rememberSpeak
import es.pictorario.app.ui.common.speakOnTap
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
    val notice = rememberNotice()
    val speak = rememberSpeak()

    // Reading aloud hangs off the deliberate taps only. Selecting by swiping the
    // carousel, or the automatic pick of whatever is happening now, stay silent:
    // the voice answers a finger on a pictogram, it is not a running commentary.
    val selectAndSpeak: (Int) -> Unit = { index ->
        selected = index
        sequence.activities.getOrNull(index)?.let { speak(it.description) }
    }

    // Whatever is happening right now is selected on arrival, and again as the
    // day moves on, unless the user has picked something else in the meantime.
    LaunchedEffect(now.hour, now.minute, sequence.activities) {
        val minutes = now.hour * 60 + now.minute
        val current = sequence.activities.indexOfFirst { it.contains(minutes) }
        if (current >= 0 && selected == -1) selected = current
    }

    BackHandler { state.navigateHome() }

    val landscape = isLandscape()

    Box(Modifier.fillMaxSize()) {
        // The dial is a tall shape — 1.3 times as high as it is wide — so
        // sideways it cannot sit above the carousel. It is fitted to the height
        // instead, and the screen becomes three columns: the dial, the controls
        // and the time, and the activity. Either way the board is still
        // measured against its own width, which its geometry assumes.
        val dial: @Composable (Modifier) -> Unit = { modifier ->
            DialArea(
                sequence = sequence,
                state = state,
                sequenceIndex = sequenceIndex,
                now = now,
                selected = selected,
                screenHeightPx = screenHeightPx,
                notice = notice,
                onSelect = selectAndSpeak,
                overlayControls = !landscape,
                modifier = modifier.aspectRatio(
                    if (landscape) DIAL_ASPECT_RATIO else BOARD_ASPECT_RATIO,
                    landscape,
                ),
            )
        }
        val carousel: @Composable (Modifier) -> Unit = { modifier ->
            if (selected in sequence.activities.indices) {
                ActivityPager(
                    sequence = sequence,
                    selectedIndex = selected,
                    now = now,
                    format24h = settings.format24h,
                    repository = state.pictograms,
                    onSelect = { selected = it },
                    onSelectThumbnail = selectAndSpeak,
                    modifier = modifier,
                )
            }
        }

        if (landscape) {
            Row(Modifier.fillMaxSize()) {
                dial(Modifier.fillMaxHeight())
                BoardControls(
                    sequence = sequence,
                    state = state,
                    sequenceIndex = sequenceIndex,
                    now = now,
                    notice = notice,
                    vertical = true,
                    modifier = Modifier.width(CONTROLS_COLUMN_WIDTH).fillMaxHeight(),
                )
                carousel(Modifier.weight(1f).fillMaxHeight())
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                dial(Modifier.readableWidth())
                carousel(Modifier.fillMaxSize())
            }
        }

        // Floats over everything, near the bottom, so it never hides the dial.
        Notice(notice)
    }

    // An alarm that has just gone off takes over the screen until acknowledged.
    state.firedAlarm?.let { (alarmSequence, alarmActivity) ->
        if (alarmSequence == sequenceIndex) {
            sequence.activities.getOrNull(alarmActivity)?.let { fired ->
                LaunchedEffect(fired) { selected = alarmActivity }
                AlarmDialog(
                    activity = fired,
                    repository = state.pictograms,
                    format24h = settings.format24h,
                    onDismiss = state::dismissFiredAlarm,
                )
            }
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
    notice: NoticeController,
    onSelect: (Int) -> Unit,
    /** False when the caller draws the time and the buttons in a column of their own. */
    overlayControls: Boolean,
    modifier: Modifier = Modifier,
) {
    val settings = state.settings
    val density = LocalDensity.current
    var dialWidthPx by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
    val geometry = remember(sequence, dialWidthPx) {
        if (dialWidthPx > 0f) geometryFor(sequence, dialWidthPx) else null
    }

    Box(
        modifier
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

        if (overlayControls && sequence.board.timeIndicator != TimeIndicator.NONE) {
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

        // In portrait the controls sit in the gap the dial leaves below itself.
        // Sideways there is no such gap — the dial is as tall as the window —
        // so the caller places them underneath instead.
        if (overlayControls) {
            BoardControls(
                sequence = sequence,
                state = state,
                sequenceIndex = sequenceIndex,
                now = now,
                notice = notice,
                vertical = false,
                modifier = Modifier.align(Alignment.BottomCenter).padding(vertical = 30.dp),
            )
        }

    }
}

/**
 * What the board offers besides itself: the time in figures, a way to change
 * how it is drawn, and a way out. With the app locked the last two merge into
 * the padlock, which only the long press an adult knows gets past.
 *
 * Laid out in a row under the dial in portrait, and as its own column between
 * the dial and the carousel when the screen is wider than it is tall.
 */
@Composable
private fun BoardControls(
    sequence: Sequence,
    state: PictorarioState,
    sequenceIndex: Int,
    now: LocalTime,
    notice: NoticeController,
    vertical: Boolean,
    modifier: Modifier = Modifier,
) {
    val settings = state.settings

    val clock: @Composable () -> Unit = {
        if (sequence.board.timeIndicator != TimeIndicator.NONE) {
            Text(
                // "12:57 del mediodía" does not fit across a narrow column at
                // the size it takes over the dial, and clipping the time is
                // worse than shrinking it.
                text = TimeFormat.digitalClock(now.hour, now.minute, settings.format24h),
                fontSize = if (vertical) 22.sp else 34.sp,
                fontWeight = FontWeight.Bold,
                color = DigitalClockColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    // Always offered, lock or no lock. Leaving the board is not editing, and a
    // child using the app on their own has to be able to go back without asking
    // an adult for the unlock gesture.
    val closeButton: @Composable () -> Unit = {
        Help("Volver a la portada", modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = state::navigateHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .speakOnTap("Cerrar visualización"),
            ) {
                Text("Cerrar visualización", fontSize = 18.sp, textAlign = TextAlign.Center)
            }
        }
    }

    // With the app locked this becomes the padlock, because changing the board
    // style writes to the stored sequence and that is exactly what the lock is
    // for. The padlock only unlocks: it does not navigate anywhere.
    val viewButton: @Composable () -> Unit = {
        if (settings.appProtected) {
            UnlockPadlock(state)
        } else {
            Help("Cambiar entre reloj de mañana, de tarde, de 24 horas y secuencia completa") {
                Image(
                    painter = painterResource(boardIcon(sequence.board.type)),
                    contentDescription = "Cambiar vista",
                    modifier = Modifier.size(60.dp).speakOnTap("Cambiar vista").clickable {
                        // The board changes underneath without any transition,
                        // so the notice is what tells the user where they
                        // landed.
                        val next = BoardType.entries[
                            (sequence.board.type.ordinal + 1) % BoardType.entries.size,
                        ]
                        state.cycleBoardType(sequenceIndex)
                        notice.show("Cambiando vista a ${BOARD_TYPE_LABELS[next.ordinal]}")
                    },
                )
            }
        }
    }

    if (vertical) {
        Column(
            modifier = modifier.padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        ) {
            clock()
            viewButton()
            closeButton()
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(Modifier.weight(1f)) { closeButton() }
            viewButton()
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
private fun boardIcon(boardType: BoardType): Int = when (boardType) {
    BoardType.MORNING_12H -> R.drawable.manana
    BoardType.AFTERNOON_12H -> R.drawable.tarde
    BoardType.DAY_24H -> R.drawable.dia
    BoardType.FULL_SEQUENCE -> R.drawable.fila
}

private val DigitalClockColor = Color(0xFF909090)

/** Width of the middle column in landscape: enough for a 60 dp icon and a label. */
private val CONTROLS_COLUMN_WIDTH = 200.dp
