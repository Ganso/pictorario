package es.pictorario.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Colour chooser for the clock hands, replacing the `ColorPickerDialog` of
 * B4A's dialogs library.
 *
 * A fixed palette rather than a hue wheel: the choice only has to distinguish
 * three hands at a glance, and every swatch here stays legible on the white
 * face — which a free picker cannot promise.
 */
@Composable
fun ColorPickerDialog(
    title: String,
    selected: Long,
    onAccept: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    var current by remember(selected) { mutableLongStateOf(selected) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(Color(current.toInt()))
                        .border(1.dp, Color.Gray),
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier.padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(HandColors) { color ->
                        Box(
                            Modifier
                                .aspectRatio(1f)
                                .background(Color(color.toInt()))
                                .border(
                                    width = if (color == current) 3.dp else 1.dp,
                                    color = if (color == current) Color.Black else Color.Gray,
                                )
                                .clickable { current = color },
                        )
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = { onAccept(current) }) { Text("Aceptar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
    )
}

/** Saturated, high-contrast colours; the three defaults come first. */
private val HandColors = listOf(
    0xFF000000, 0xFF0000FF, 0xFFFF0000, 0xFF008000, 0xFFFF8000,
    0xFF800080, 0xFF008080, 0xFF804000, 0xFFC00060, 0xFF404040,
    0xFF0080FF, 0xFF00A000, 0xFFB00000, 0xFF606000, 0xFF8000FF,
)

/** Swatch used in the settings screen to show and change a hand's colour. */
@Composable
fun ColorSwatch(color: Long, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(Color(color.toInt()))
            .border(1.dp, Color.Gray)
            .clickable(onClick = onClick),
    )
}

/** Row of the three hand swatches, in the order the settings screen lists them. */
@Composable
fun HandColorRow(
    hourColor: Long,
    minuteColor: Long,
    secondColor: Long,
    onPick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf(hourColor, minuteColor, secondColor).forEachIndexed { index, color ->
            ColorSwatch(
                color = color,
                onClick = { onPick(index) },
                modifier = Modifier.weight(1f).aspectRatio(1f),
            )
        }
    }
}
