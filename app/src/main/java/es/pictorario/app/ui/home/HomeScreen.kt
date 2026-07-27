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

    val nextAlarm = remember(state.data) {
        state.data?.let {
            val now = LocalTime.now()
            AlarmCalculator.next(it, now.hour * 60 + now.minute)
        }
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 10.dp)) {
            item { Header() }

            itemsIndexed(sequences) { index, sequence ->
                SequenceRow(
                    sequence = sequence,
                    state = state,
                    showGear = !settings.appProtected,
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
                        enabled = sequences.size < MAX_SEQUENCES,
                        onClick = { state.startEditing(null) },
                    )
                    HomeButton("Configuración") { state.navigateTo(Screen.Settings) }
                    HomeButton("Acerca de Pictorario") { state.navigateTo(Screen.About) }
                }
                HomeButton("Salir", onClick = onExit)
            }
        }

        if (settings.appProtected) {
            Image(
                painter = painterResource(R.drawable.candado),
                contentDescription = "Desbloquear la aplicación",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .size(40.dp)
                    .lockGesture { state.updateSettings { it.copy(appProtected = false) } },
            )
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
    onOpen: () -> Unit,
    onGear: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(90.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PictogramImage(
            pictogramId = sequence.pictogramId,
            repository = state.pictograms,
            size = 80.dp,
            contentDescription = sequence.description,
            modifier = Modifier.size(80.dp).clickable(onClick = onOpen),
        )
        Text(
            text = sequence.description,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp)
                .clickable(onClick = onOpen),
        )
        if (showGear) {
            Image(
                painter = painterResource(R.drawable.engranaje),
                contentDescription = "Opciones de ${sequence.description}",
                modifier = Modifier.size(40.dp).clickable(onClick = onGear),
            )
        }
    }
}

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
private fun HomeButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth().height(60.dp).padding(vertical = 4.dp),
    ) {
        Text(text, fontSize = 20.sp)
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
