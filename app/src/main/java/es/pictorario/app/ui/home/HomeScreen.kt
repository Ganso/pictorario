package es.pictorario.app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.R
import es.pictorario.app.domain.AlarmCalculator
import es.pictorario.app.domain.MAX_SEQUENCES
import es.pictorario.app.domain.NextAlarm
import es.pictorario.app.domain.Sequence
import es.pictorario.app.domain.TimeFormat
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.Screen
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import es.pictorario.app.domain.Activity
import es.pictorario.app.ui.clock.rememberClockTick
import es.pictorario.app.ui.common.Help
import es.pictorario.app.ui.common.PictogramImage
import es.pictorario.app.ui.common.lockGesture
import java.time.LocalTime

/**
 * The front page: every sequence, the next alarm, and the way in to everything
 * else. Follows the layout `DibujarPortada` built by hand in
 * `pictorario.b4a:176-282` — an 80 dp pictogram, the description, and a gear on
 * the right of each 90 dp row.
 */
@Composable
fun HomeScreen(state: PictorarioState, onExit: () -> Unit) {
    val settings = state.settings
    val sequences = state.sequences
    var menuFor by remember { mutableStateOf<Int?>(null) }
    var confirmDelete by remember { mutableStateOf<Int?>(null) }

    // Ticks once a minute, so the badges and the next alarm stay right as the
    // day moves on instead of freezing at whatever time the screen opened.
    val now = rememberClockTick(needsSeconds = false)
    val minutesOfDay = now.hour * 60 + now.minute

    val nextAlarm = remember(state.data, minutesOfDay) {
        state.data?.let { AlarmCalculator.next(it, minutesOfDay) }
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 10.dp)) {
            item { Header() }

            itemsIndexed(sequences) { index, sequence ->
                SequenceRow(
                    sequence = sequence,
                    state = state,
                    showGear = !settings.appProtected,
                    // The alarm badge tracks what will actually happen: a
                    // sequence set to notify says nothing while the global
                    // switch is off.
                    hasAlarm = settings.alarmsEnabled && sequence.notifications,
                    runningActivity = sequence.activities.firstOrNull {
                        it.contains(minutesOfDay)
                    },
                    format24h = settings.format24h,
                    onOpen = { state.navigateTo(Screen.Clock(index)) },
                    onGear = { menuFor = index },
                )
            }

            if (nextAlarm != null) {
                item { NextAlarmRow(nextAlarm, sequences, state) }
            }

            item {
                if (!settings.appProtected) {
                    HomeButton(
                        text = "Crear Secuencia",
                        help = if (sequences.size < MAX_SEQUENCES) {
                            "Crear un horario nuevo"
                        } else {
                            "Ya tienes el máximo de $MAX_SEQUENCES secuencias"
                        },
                        enabled = sequences.size < MAX_SEQUENCES,
                        onClick = { state.startEditing(null) },
                    )
                    HomeButton("Configuración", "Alarmas, protección, formato horario y colores") {
                        state.navigateTo(Screen.Settings)
                    }
                    HomeButton("Acerca de Pictorario", "Créditos, licencia y versión") {
                        state.navigateTo(Screen.About)
                    }
                }
                HomeButton("Salir", "Cerrar Pictorario", onClick = onExit)
            }
        }

        if (settings.appProtected) {
            Help(
                text = "Para desbloquear: toca una vez y después mantén pulsado",
                modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.candado),
                    contentDescription = "Desbloquear la aplicación",
                    modifier = Modifier
                        .size(40.dp)
                        .lockGesture { state.updateSettings { it.copy(appProtected = false) } },
                )
            }
        }
    }

    menuFor?.let { index ->
        SequenceMenu(
            sequence = sequences[index],
            canDuplicate = sequences.size < MAX_SEQUENCES,
            onEdit = { menuFor = null; state.startEditing(index) },
            onDelete = { menuFor = null; confirmDelete = index },
            onDuplicate = { menuFor = null; state.duplicateSequence(index) },
            onDismiss = { menuFor = null },
        )
    }

    confirmDelete?.let { index ->
        AlertDialog(
            onDismissRequest = { confirmDelete = null },
            title = { Text("Borrar secuencia") },
            text = { Text("¿Seguro que quieres borrar «${sequences[index].description}»?") },
            confirmButton = {
                TextButton(onClick = { confirmDelete = null; state.deleteSequence(index) }) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = null }) { Text("Cancelar") }
            },
        )
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth().height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.logotipo),
            contentDescription = null,
            modifier = Modifier.size(80.dp),
        )
        Text(
            text = "Pictorario",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

@Composable
private fun SequenceRow(
    sequence: Sequence,
    state: PictorarioState,
    showGear: Boolean,
    hasAlarm: Boolean,
    runningActivity: Activity?,
    format24h: Boolean,
    onOpen: () -> Unit,
    onGear: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(90.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Help("Abrir «${sequence.description}»") {
            PictogramImage(
                pictogramId = sequence.pictogramId,
                repository = state.pictograms,
                size = 80.dp,
                contentDescription = sequence.description,
                modifier = Modifier.size(80.dp).clickable(onClick = onOpen),
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp)
                .clickable(onClick = onOpen),
        ) {
            Text(sequence.description, fontSize = 16.sp)

            if (hasAlarm || runningActivity != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 6.dp),
                ) {
                    if (hasAlarm) {
                        Help("Esta secuencia tiene las alarmas activadas") {
                            Image(
                                painter = painterResource(R.drawable.alarma),
                                contentDescription = "Alarmas activadas",
                                modifier = Modifier.size(22.dp),
                            )
                        }
                    }
                    runningActivity?.let { activity ->
                        val until = TimeFormat.time(activity.endHour, activity.endMinute, format24h)
                        Help("Ahora mismo: ${activity.description}, hasta las $until") {
                            RunningNowBadge(activity.description)
                        }
                    }
                }
            }
        }

        if (showGear) {
            Help("Editar, borrar o duplicar «${sequence.description}»") {
                Image(
                    painter = painterResource(R.drawable.engranaje),
                    contentDescription = "Opciones de ${sequence.description}",
                    modifier = Modifier.size(40.dp).clickable(onClick = onGear),
                )
            }
        }
    }
}

/** Marks a sequence whose activity is under way right now. */
@Composable
private fun RunningNowBadge(description: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(RunningNow),
        )
        Text(
            text = description,
            fontSize = 13.sp,
            color = RunningNow,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 6.dp),
        )
    }
}

/** Green of a task in progress; darkened so it reads on white. */
private val RunningNow = Color(0xFF2E7D32)

@Composable
private fun NextAlarmRow(
    alarm: NextAlarm,
    sequences: List<Sequence>,
    state: PictorarioState,
) {
    val sequence = sequences.getOrNull(alarm.sequenceIndex) ?: return
    val activity = sequence.activities.getOrNull(alarm.activityIndex) ?: return
    val whenText = if (alarm.isTomorrow) "Mañana a las" else "Hoy a las"
    val time = TimeFormat.time(alarm.hour, alarm.minute, state.settings.format24h)

    Row(
        modifier = Modifier.fillMaxWidth().height(90.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Próxima alarma:\n$whenText $time\n" +
                "${sequence.description} ➞ ${activity.description}",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.weight(1f),
        )
        PictogramImage(
            pictogramId = activity.pictogramId,
            repository = state.pictograms,
            size = 60.dp,
            modifier = Modifier.size(60.dp),
        )
    }
}

@Composable
private fun HomeButton(
    text: String,
    help: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Help(help, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(vertical = 4.dp),
        ) {
            Text(text, fontSize = 20.sp)
        }
    }
}

@Composable
private fun SequenceMenu(
    sequence: Sequence,
    canDuplicate: Boolean,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDuplicate: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(sequence.description) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                TextButton(onClick = onEdit, modifier = Modifier.fillMaxWidth()) {
                    Text("Editar", style = MaterialTheme.typography.bodyLarge)
                }
                TextButton(onClick = onDelete, modifier = Modifier.fillMaxWidth()) {
                    Text("Borrar", style = MaterialTheme.typography.bodyLarge)
                }
                TextButton(
                    onClick = onDuplicate,
                    enabled = canDuplicate,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Duplicar", style = MaterialTheme.typography.bodyLarge)
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
    )
}
