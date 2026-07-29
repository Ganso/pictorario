package es.pictorario.app.ui.editor

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.R
import es.pictorario.app.domain.Activity
import es.pictorario.app.domain.ActivityRules
import es.pictorario.app.domain.TimeChangeOutcome
import es.pictorario.app.domain.BOARD_TYPE_LABELS
import es.pictorario.app.domain.BoardType
import es.pictorario.app.domain.MAX_ACTIVITIES
import es.pictorario.app.domain.Palette
import es.pictorario.app.domain.TIME_INDICATOR_LABELS
import es.pictorario.app.domain.TimeFormat
import es.pictorario.app.domain.TimeIndicator
import es.pictorario.app.ui.PictogramTarget
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.common.ConfirmDialog
import es.pictorario.app.ui.common.Help
import es.pictorario.app.ui.common.Notice
import es.pictorario.app.ui.common.rememberNotice
import es.pictorario.app.ui.common.OptionListDialog
import es.pictorario.app.ui.common.PictogramImage
import es.pictorario.app.ui.common.PictorarioTimePicker
import es.pictorario.app.ui.common.readableWidth
import es.pictorario.app.ui.theme.FieldBorder
import es.pictorario.app.ui.theme.FieldSurface

private val CellSize = 70.dp
private val CellGap = 5.dp

/** Which time field a picker is currently open for. */
private data class TimeEdit(val activityIndex: Int, val isStart: Boolean)

/**
 * Creates and edits a sequence. Everything is applied to a draft held in
 * [PictorarioState]; nothing reaches storage until "Aceptar".
 *
 * Follows the layout `DibujarConfigurarSecuencia` built by hand in
 * `ConfigurarSecuencia.bas:142-334`: a header of settings, then two rows per
 * activity, then the buttons.
 */
@Composable
fun EditorScreen(state: PictorarioState) {
    val draft = state.draft ?: return
    val format24h = state.settings.format24h

    var boardPicker by remember { mutableStateOf(false) }
    var indicatorPicker by remember { mutableStateOf(false) }
    var timeEdit by remember { mutableStateOf<TimeEdit?>(null) }
    var activityMenu by remember { mutableStateOf<Int?>(null) }
    var confirmCancel by remember { mutableStateOf(false) }
    val notice = rememberNotice()

    BackHandler { confirmCancel = true }

    Box(Modifier.fillMaxSize()) {
    LazyColumn(
        modifier = Modifier.readableWidth().padding(horizontal = CellGap),
        verticalArrangement = Arrangement.spacedBy(CellGap),
    ) {
        item {
            Text(
                text = if (state.draftIndex == null) "Crear nueva secuencia" else "Editar secuencia",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().height(80.dp).padding(top = 24.dp),
            )
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = draft.description,
                    onValueChange = { text -> state.updateDraft { it.copy(description = text) } },
                    label = { Text("Descripción") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                )
                Help("Elegir el pictograma que representa la secuencia") {
                    PictogramImage(
                        pictogramId = draft.pictogramId,
                        repository = state.pictograms,
                        size = CellSize,
                        contentDescription = "Pictograma de la secuencia",
                        modifier = Modifier
                            .padding(start = CellGap)
                            .size(CellSize)
                            .clickable { state.choosePictogramFor(PictogramTarget.SequenceIcon) },
                    )
                }
            }
        }

        item {
            SettingRow(
                label = "Tipo de tablero:",
                help = "Cómo se dibuja el horario: reloj de mañana, de tarde, " +
                    "de 24 horas, o arco con sólo las horas de la secuencia",
            ) {
                ValueButton(BOARD_TYPE_LABELS[draft.board.type.ordinal]) { boardPicker = true }
            }
        }
        item {
            SettingRow(
                label = "Indicar hora actual:",
                help = "Cuántas agujas se dibujan sobre el tablero: ninguna, sólo " +
                    "la de la hora, con minutero, o también con segundero",
            ) {
                ValueButton(TIME_INDICATOR_LABELS[draft.board.timeIndicator.ordinal]) {
                    indicatorPicker = true
                }
            }
        }
        item {
            SettingRow(
                label = "Tamaño de los iconos:",
                help = "Tamaño de los pictogramas que se dibujan sobre el reloj. " +
                    "Al mínimo no se dibuja ninguno",
            ) {
                Slider(
                    value = draft.board.iconSizePercent.toFloat(),
                    onValueChange = { value ->
                        state.updateDraft {
                            it.copy(board = it.board.copy(iconSizePercent = value.toInt()))
                        }
                    },
                    valueRange = 0f..30f,
                    steps = 29,
                    modifier = Modifier.weight(1f),
                )
            }
        }
        item {
            SettingRow(
                label = "Activar alarmas:",
                help = "Avisar cuando empiece cada actividad de esta secuencia",
            ) {
                Checkbox(
                    checked = draft.notifications,
                    onCheckedChange = { checked ->
                        state.updateDraft { it.copy(notifications = checked) }
                    },
                )
            }
        }

        item {
            Text(
                text = "Actividades",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            )
        }

        itemsIndexed(draft.activities) { index, activity ->
            ActivityRow(
                index = index,
                activity = activity,
                format24h = format24h,
                state = state,
                onEditTime = { isStart -> timeEdit = TimeEdit(index, isStart) },
                onMenu = { activityMenu = index },
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(CellGap)) {
                Button(
                    onClick = {
                        state.updateDraft {
                            it.copy(activities = it.activities + ActivityRules.newActivity(it.activities))
                        }
                    },
                    enabled = draft.activities.size < MAX_ACTIVITIES,
                    modifier = Modifier.fillMaxWidth().height(CellSize),
                ) { Text("Añadir Actividad", fontSize = 16.sp) }

                Row(horizontalArrangement = Arrangement.spacedBy(CellGap)) {
                    Button(
                        onClick = state::commitDraft,
                        enabled = draft.activities.isNotEmpty(),
                        modifier = Modifier.weight(1f).height(CellSize),
                    ) { Text("Aceptar", fontSize = 16.sp) }
                    Button(
                        onClick = { confirmCancel = true },
                        modifier = Modifier.weight(1f).height(CellSize),
                    ) { Text("Cancelar", fontSize = 16.sp) }
                }
            }
        }
    }

        // Floats over the form so a rejected time cannot be missed.
        Notice(notice)
    }

    if (boardPicker) {
        OptionListDialog(
            title = "Tipo de tablero",
            options = BOARD_TYPE_LABELS,
            selectedIndex = draft.board.type.ordinal,
            onSelect = { chosen ->
                state.updateDraft {
                    it.copy(board = it.board.copy(type = BoardType.entries[chosen]))
                }
                boardPicker = false
            },
            onDismiss = { boardPicker = false },
        )
    }

    if (indicatorPicker) {
        OptionListDialog(
            title = "Indicar hora actual",
            options = TIME_INDICATOR_LABELS,
            selectedIndex = draft.board.timeIndicator.ordinal,
            onSelect = { chosen ->
                state.updateDraft {
                    it.copy(board = it.board.copy(timeIndicator = TimeIndicator.entries[chosen]))
                }
                indicatorPicker = false
            },
            onDismiss = { indicatorPicker = false },
        )
    }

    timeEdit?.let { edit ->
        val activity = draft.activities.getOrNull(edit.activityIndex) ?: return@let
        PictorarioTimePicker(
            title = if (edit.isStart) "Hora inicial" else "Hora final",
            // An end of 24:00 goes back into the picker as 00:00: Material's
            // clock only knows hours 0..23, and asking it for 24 throws.
            hour = if (edit.isStart) activity.startHour else activity.endHour % 24,
            minute = if (edit.isStart) activity.startMinute else activity.endMinute,
            format24h = format24h,
            onAccept = { hour, minute ->
                // Sorting and de-overlapping happen right away, as
                // OrdenarActividades did, but now the user is told when the
                // time they picked could not stand.
                val change = ActivityRules.changeTime(
                    activities = draft.activities,
                    index = edit.activityIndex,
                    isStart = edit.isStart,
                    hour = hour,
                    minute = minute,
                )
                state.updateDraft { it.copy(activities = change.activities) }
                noticeFor(change.outcome)?.let(notice::show)
                timeEdit = null
            },
            onDismiss = { timeEdit = null },
        )
    }

    activityMenu?.let { index ->
        ConfirmDialog(
            title = "Borrar actividad",
            message = "¿Seguro que quieres borrar esta actividad?",
            confirmText = "Borrar",
            onConfirm = {
                state.updateDraft { sequence ->
                    sequence.copy(
                        activities = sequence.activities.toMutableList()
                            .apply { removeAt(index) },
                    )
                }
                activityMenu = null
            },
            onDismiss = { activityMenu = null },
        )
    }

    if (confirmCancel) {
        ConfirmDialog(
            title = "Cancelar",
            message = "Se perderán los cambios de esta secuencia. ¿Continuar?",
            confirmText = "Descartar",
            onConfirm = state::discardDraft,
            onDismiss = { confirmCancel = false },
        )
    }
}

/** Turns the outcome of a time change into something worth reading, or nothing. */
private fun noticeFor(outcome: TimeChangeOutcome): String? = when (outcome) {
    is TimeChangeOutcome.Applied -> null

    is TimeChangeOutcome.Rejected -> {
        val other = outcome.clashedWith.takeIf { it.isNotBlank() }?.let { "«$it»" }
            ?: "la actividad siguiente"
        "No se ha podido poner esa hora: se solapaba con $other"
    }

    is TimeChangeOutcome.Adjusted ->
        outcome.detail.replaceFirstChar(Char::uppercaseChar)
}

@Composable
private fun SettingRow(
    label: String,
    help: String,
    content: @Composable RowScope.() -> Unit,
) {
    // La ayuda cubre la fila entera: el dedo aterriza tanto en la etiqueta como
    // en el control, y desde cualquiera de los dos debe poder pedirse.
    Help(help, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().height(CellSize),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, fontSize = 16.sp, modifier = Modifier.fillMaxWidth(0.4f))
            content()
        }
    }
}

@Composable
private fun ValueButton(text: String, onClick: () -> Unit) {
    Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(CellSize)
                .background(FieldSurface)
                .border(1.dp, FieldBorder)
                .clickable(onClick = onClick),
        ) {
        Text(text, fontSize = 16.sp, textAlign = TextAlign.Center)
    }
}

@Composable
private fun ActivityRow(
    index: Int,
    activity: Activity,
    format24h: Boolean,
    state: PictorarioState,
    onEditTime: (isStart: Boolean) -> Unit,
    onMenu: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(CellGap),
        modifier = Modifier.padding(bottom = CellGap * 3),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = activity.description,
                onValueChange = { text ->
                    state.updateDraft { sequence ->
                        sequence.copy(
                            activities = sequence.activities.toMutableList()
                                .also { it[index] = activity.copy(description = text) },
                        )
                    }
                },
                singleLine = true,
                // The row is tinted with the colour its sector will have on the
                // dial, which is how the original tied the two screens together.
                modifier = Modifier
                    .weight(1f)
                    .background(Color(Palette.color(index).toInt())),
            )
            Help("Elegir el pictograma de esta actividad") {
                PictogramImage(
                    pictogramId = activity.pictogramId,
                    repository = state.pictograms,
                    size = CellSize,
                    contentDescription = "Pictograma de la actividad",
                    modifier = Modifier
                        .padding(start = CellGap)
                        .size(CellSize)
                        .clickable {
                            state.choosePictogramFor(PictogramTarget.ActivityIcon(index))
                        },
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CellGap),
        ) {
            TimeButton(
                label = "Desde",
                help = "Cambiar la hora a la que empieza la actividad",
                hour = activity.startHour,
                minute = activity.startMinute,
                format24h = format24h,
                modifier = Modifier.weight(1f),
            ) { onEditTime(true) }
            TimeButton(
                label = "Hasta",
                help = "Cambiar la hora a la que termina la actividad. " +
                    "Elige las 12 de la noche para que dure hasta el final del día",
                hour = activity.endHour,
                minute = activity.endMinute,
                format24h = format24h,
                modifier = Modifier.weight(1f),
            ) { onEditTime(false) }
            Help("Borrar esta actividad") {
                Image(
                    painter = painterResource(R.drawable.engranaje),
                    contentDescription = "Opciones de la actividad",
                    modifier = Modifier.size(CellSize).clickable(onClick = onMenu),
                )
            }
        }
    }
}

@Composable
private fun TimeButton(
    label: String,
    help: String,
    hour: Int,
    minute: Int,
    format24h: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(modifier) {
        Help(help, modifier = Modifier.fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(CellSize)
                .background(FieldSurface)
                .border(1.dp, FieldBorder)
                .clickable(onClick = onClick),
        ) {
            Text(
                text = "$label\n${TimeFormat.time(hour, minute, format24h)}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
        }
    }
}
