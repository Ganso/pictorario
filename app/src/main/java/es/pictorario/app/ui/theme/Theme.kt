package es.pictorario.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Pictorario is used by children who rely on a stable, high-contrast layout, so
 * the palette is fixed and light regardless of the system setting. A dark theme
 * is tracked as a future improvement, not a drop-in [isSystemInDarkTheme] switch.
 */
private val PictorarioColors = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

@Composable
fun PictorarioTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = PictorarioColors, content = content)
}
