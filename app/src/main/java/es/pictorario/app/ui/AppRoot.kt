package es.pictorario.app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.pictorario.app.ui.home.HomeScreen
import es.pictorario.app.ui.theme.PictorarioTheme

/**
 * Hosts every screen and owns the back-button policy.
 *
 * The B4A version swallowed Back on all screens. Here it is only suppressed
 * while the parental lock is on — which is when it actually matters — so that
 * with the app unlocked navigation behaves the way Android users expect.
 */
@Composable
fun AppRoot(state: PictorarioState, onExit: () -> Unit) {
    PictorarioTheme {
        // Since Android 15 apps draw edge to edge by default, so without this
        // the status bar sits on top of the header.
        Surface(Modifier.fillMaxSize().safeDrawingPadding()) {
            if (!state.isReady) return@Surface

            BackHandler(enabled = state.settings.appProtected) { /* locked: ignore Back */ }

            when (val screen = state.screen) {
                is Screen.Home -> HomeScreen(state, onExit)
                is Screen.Clock -> Pending("Reloj", "Secuencia ${screen.sequenceIndex}", state)
                is Screen.Editor -> Pending("Editor", describe(screen), state)
                is Screen.Settings -> Pending("Configuración", null, state)
                is Screen.About -> Pending("Acerca de", null, state)
            }
        }
    }
}

private fun describe(screen: Screen.Editor): String =
    screen.sequenceIndex?.let { "Secuencia $it" } ?: "Secuencia nueva"

/** Stand-in for the screens still to be built, so navigation can be exercised. */
@Composable
private fun Pending(title: String, detail: String?, state: PictorarioState) {
    BackHandler(enabled = !state.settings.appProtected) { state.navigateHome() }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        detail?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
        Text(
            "Pendiente de implementar",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 8.dp),
        )
        Button(onClick = state::navigateHome, modifier = Modifier.padding(top = 24.dp)) {
            Text("Volver a la portada")
        }
    }
}
