package es.pictorario.app.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

/**
 * A list of choices in a dialog, the stand-in for B4A's `InputList`. Picking an
 * entry closes the dialog straight away, as the original did.
 */
@Composable
fun OptionListDialog(
    title: String,
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                options.forEachIndexed { index, option ->
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (index == selectedIndex) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(index) }
                            .padding(vertical = 14.dp),
                    )
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
    )
}

/**
 * Material's clock picker in a dialog, replacing the `TimeDialog` of B4A's
 * dialogs library. Honours the 12/24-hour setting just as the original did.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictorarioTimePicker(
    title: String,
    hour: Int,
    minute: Int,
    format24h: Boolean,
    onAccept: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val pickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = format24h,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { TimePicker(state = pickerState) },
        confirmButton = {
            TextButton(onClick = { onAccept(pickerState.hour, pickerState.minute) }) {
                Text("Aceptar")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
    )
}

/** Something to read and acknowledge. Unlike [ConfirmDialog] there is nothing to cancel. */
@Composable
fun MessageDialog(title: String, message: String, onDismiss: () -> Unit) =
    MessageDialog(title, AnnotatedString(message), onDismiss)

/** As above, for a message that needs a word or two picked out. */
@Composable
fun MessageDialog(title: String, message: AnnotatedString, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        // Scrolls rather than clips: the what's-new text is the longest in the
        // app, and a small screen with a large system font would cut it off.
        text = { Text(message, modifier = Modifier.verticalScroll(rememberScrollState())) },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Aceptar") } },
    )
}

/** Yes/no confirmation, used before anything destructive. */
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(message) },
        confirmButton = { TextButton(onClick = onConfirm) { Text(confirmText) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
    )
}
