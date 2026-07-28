package es.pictorario.app.ui.clock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.data.PictogramRepository
import es.pictorario.app.domain.Activity
import es.pictorario.app.domain.TimeFormat
import es.pictorario.app.ui.common.PictogramImage

/**
 * What the child sees when an activity begins.
 *
 * The board is already behind it, so this is the same idea as the original's
 * `Msgbox2` with the pictogram as its icon: something unmistakable, large, and
 * dismissed by an adult rather than by tapping anywhere.
 */
@Composable
fun AlarmDialog(
    activity: Activity,
    repository: PictogramRepository,
    format24h: Boolean,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        // Only the button dismisses it: a stray tap by the child should not
        // make the alarm disappear before anyone has seen it.
        onDismissRequest = {},
        title = {
            Text(
                text = "¡Empieza ahora!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PictogramImage(
                    pictogramId = activity.pictogramId,
                    repository = repository,
                    size = 160.dp,
                    contentDescription = activity.description,
                    modifier = Modifier.size(160.dp),
                )
                Text(
                    text = activity.description,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                )
                Text(
                    text = "hasta las " +
                        TimeFormat.time(activity.endHour, activity.endMinute, format24h),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
                Text("Entendido", fontSize = 18.sp)
            }
        },
    )
}
