package es.pictorario.app.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/**
 * Wraps a control so that holding a finger on it explains what it does.
 *
 * The app is used by adults who set it up occasionally and by children who
 * cannot read the labels, so a great deal rests on icons alone — gears,
 * padlocks, sunrises. A long press is the one gesture that costs nothing to
 * anybody who is not looking for it.
 *
 * The text also becomes the accessibility description, so screen readers say
 * the same thing.
 *
 * **Do not hand this a `Modifier.weight`.** `TooltipBox` does not carry a row
 * weight through to its anchor, so a `fillMaxWidth` inside resolves against the
 * whole row and shoves its siblings off screen. Inside a `Row`, put the weight
 * on a plain `Box` and place the `Help` within it.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Help(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = { PlainTooltip { Text(text, fontSize = 14.sp) } },
        state = rememberTooltipState(),
        modifier = modifier,
    ) {
        Box { content() }
    }
}
