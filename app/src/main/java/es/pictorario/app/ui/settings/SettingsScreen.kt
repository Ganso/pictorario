package es.pictorario.app.ui.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import es.pictorario.app.R
import es.pictorario.app.alarm.AlarmScheduler
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.common.ColorPickerDialog
import es.pictorario.app.ui.common.ConfirmDialog
import es.pictorario.app.ui.common.Help
import es.pictorario.app.ui.common.HandColorRow

private const val LOCK_EXPLANATION =
    "Con la aplicación protegida desaparecen los botones de edición y sólo se " +
        "puede ver el horario.\n\nPara desbloquearla, toca el candado una vez y " +
        "después mantenlo pulsado."

/** Global preferences. Port of `Configuracion.bas`. */
@Composable
fun SettingsScreen(state: PictorarioState) {
    val context = LocalContext.current
    val settings = state.settings
    var colorPicker by remember { mutableStateOf<Int?>(null) }
    var confirmReset by remember { mutableStateOf(false) }
    var lockExplanation by remember { mutableStateOf(false) }

    BackHandler { state.navigateHome() }

    Column(
        modifier = Modifier
            .fillMaxSize()
                        .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Configuración", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        SettingRow(
            icon = R.drawable.alarma,
            label = "Activar alarmas",
            help = "Interruptor general: con esto apagado no suena ninguna alarma",
        ) {
            Checkbox(
                checked = settings.alarmsEnabled,
                onCheckedChange = { on -> state.updateSettings { it.copy(alarmsEnabled = on) } },
            )
        }

        SettingRow(
            icon = R.drawable.llave,
            label = "Proteger aplicación",
            help = "Oculta los botones de edición para que el niño no pueda cambiar nada",
        ) {
            Checkbox(
                checked = settings.appProtected,
                onCheckedChange = { on ->
                    state.updateSettings { it.copy(appProtected = on) }
                    if (on) lockExplanation = true
                },
            )
        }

        // Exact alarms are opt-in: without them the app still works, so the row
        // only appears while the permission is missing and simply hands over to
        // the system screen that grants it.
        if (!AlarmScheduler.canScheduleExact(context)) {
            Column {
                Text(
                    text = "Los avisos pueden retrasarse unos minutos. Para que " +
                        "suenen a la hora exacta, concede el permiso de alarmas.",
                    fontSize = 14.sp,
                )
                Help(
                    "Abre los ajustes de Android donde se concede el permiso",
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        onClick = {
                            AlarmScheduler.exactAlarmSettingsIntent(context)
                                ?.let { runCatching { context.startActivity(it) } }
                        },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    ) { Text("Permitir avisos puntuales") }
                }
            }
        }

        SettingRow(
            label = "Formato horario",
            help = "Alterna entre 12 horas con a.m. y p.m., y 24 horas",
        ) {
            Button(onClick = { state.updateSettings { it.copy(format24h = !it.format24h) } }) {
                Text(if (settings.format24h) "24 horas" else "12 horas")
            }
        }

        Help("Toca cada cuadro para cambiar el color de esa aguja") {
            Text("Colores del reloj (horario, minutero y segundero)", fontSize = 16.sp)
        }
        HandColorRow(
            hourColor = settings.hourColor,
            minuteColor = settings.minuteColor,
            secondColor = settings.secondColor,
            onPick = { colorPicker = it },
            modifier = Modifier.fillMaxWidth(0.6f),
        )

        Help(
            "Borra tus secuencias y los pictogramas descargados, y devuelve las de ejemplo",
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = { confirmReset = true },
                modifier = Modifier.fillMaxWidth().height(60.dp),
            ) { Text("Reiniciar configuración") }
        }

        Help("Volver a la portada", modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = state::navigateHome,
                modifier = Modifier.fillMaxWidth().height(60.dp),
            ) { Text("Volver a la portada") }
        }
    }

    colorPicker?.let { index ->
        val current = when (index) {
            0 -> settings.hourColor
            1 -> settings.minuteColor
            else -> settings.secondColor
        }
        ColorPickerDialog(
            title = when (index) {
                0 -> "Color del horario"
                1 -> "Color del minutero"
                else -> "Color del segundero"
            },
            selected = current,
            onAccept = { chosen ->
                state.updateSettings {
                    when (index) {
                        0 -> it.copy(hourColor = chosen)
                        1 -> it.copy(minuteColor = chosen)
                        else -> it.copy(secondColor = chosen)
                    }
                }
                colorPicker = null
            },
            onDismiss = { colorPicker = null },
        )
    }

    if (lockExplanation) {
        ConfirmDialog(
            title = "Aplicación protegida",
            message = LOCK_EXPLANATION,
            confirmText = "Entendido",
            onConfirm = { lockExplanation = false },
            onDismiss = { lockExplanation = false },
        )
    }

    if (confirmReset) {
        ConfirmDialog(
            title = "Reiniciar configuración",
            message = "Se borrarán todas tus secuencias y los pictogramas " +
                "descargados, y volverán las de ejemplo. ¿Continuar?",
            confirmText = "Reiniciar",
            onConfirm = {
                confirmReset = false
                state.resetEverything()
                state.navigateHome()
            },
            onDismiss = { confirmReset = false },
        )
    }
}

@Composable
private fun SettingRow(
    label: String,
    help: String,
    icon: Int? = null,
    content: @Composable () -> Unit,
) {
    // The whole row is the target, not just the label: the finger naturally
    // lands on the control, and holding it there is what asks for help.
    Help(help, modifier = Modifier.fillMaxWidth()) {
    Row(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, fontSize = 16.sp, modifier = Modifier.weight(1f))
        icon?.let {
            Image(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.size(48.dp).padding(end = 8.dp),
            )
        }
        content()
    }
    }
}
