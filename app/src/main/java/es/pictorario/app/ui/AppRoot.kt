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
import es.pictorario.app.ui.clock.ClockScreen
import es.pictorario.app.ui.editor.EditorScreen
import es.pictorario.app.BuildConfig
import es.pictorario.app.ui.about.AboutScreen
import es.pictorario.app.ui.about.VERSION_CHANGES
import es.pictorario.app.ui.common.ConfirmDialog
import es.pictorario.app.ui.home.HomeScreen
import es.pictorario.app.ui.settings.SettingsScreen
import es.pictorario.app.ui.picker.PickerScreen
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

            // What's-new, shown once per update. The original compared the same
            // stored versionCode in Main (pictorario.b4a:154-173).
            if (state.settings.installedVersion != BuildConfig.VERSION_CODE) {
                ConfirmDialog(
                    title = "Novedades de esta versión",
                    message = VERSION_CHANGES,
                    confirmText = "Aceptar",
                    onConfirm = {
                        state.updateSettings { it.copy(installedVersion = BuildConfig.VERSION_CODE) }
                    },
                    onDismiss = {
                        state.updateSettings { it.copy(installedVersion = BuildConfig.VERSION_CODE) }
                    },
                )
            }

            when (val screen = state.screen) {
                is Screen.Home -> HomeScreen(state, onExit)
                is Screen.Clock -> ClockScreen(state, screen.sequenceIndex)
                is Screen.Editor -> EditorScreen(state)
                is Screen.Picker -> PickerScreen(state)
                is Screen.Settings -> SettingsScreen(state)
                is Screen.About -> AboutScreen(state)
            }
        }
    }
}

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
