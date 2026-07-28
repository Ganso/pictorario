package es.pictorario.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.alarm.AlarmScheduler

private val BannerBackground = Color(0xFFFFF4E0)
private val BannerBorder = Color(0xFFE8B84B)

/**
 * Asks for the exact-alarm permission, in the place and at the moment it
 * actually matters.
 *
 * Android grants this one from its own settings screen rather than a dialog,
 * which makes it easy to miss entirely — and missing it means the alarms arrive
 * late, or during Doze not until the device next wakes. Burying the request in
 * the settings screen was not enough: it belongs in front of anyone who has
 * turned an alarm on.
 *
 * Shows nothing when the permission is already there, so it disappears for good
 * once granted.
 */
@Composable
fun ExactAlarmBanner(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    if (AlarmScheduler.canScheduleExact(context)) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(BannerBackground)
            .border(1.dp, BannerBorder, RoundedCornerShape(12.dp))
            .padding(16.dp),
    ) {
        Text(
            text = "Las alarmas pueden llegar tarde",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Android exige un permiso aparte para avisar a la hora exacta. " +
                "Sin él, el aviso puede retrasarse bastante si el móvil está en reposo.\n\n" +
                "Al pulsar el botón se abren los ajustes de Android: activa " +
                "«Alarmas y recordatorios» y vuelve aquí.",
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp),
        )
        Help("Abre los ajustes de Android donde se concede el permiso") {
            Button(
                onClick = {
                    AlarmScheduler.exactAlarmSettingsIntent(context)
                        ?.let { runCatching { context.startActivity(it) } }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
            ) {
                Text("Permitir avisos a la hora exacta")
            }
        }
    }
}
