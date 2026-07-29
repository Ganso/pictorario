package es.pictorario.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import es.pictorario.app.BuildConfig
import es.pictorario.app.ui.about.AboutScreen
import es.pictorario.app.ui.about.VERSION_CHANGES
import es.pictorario.app.ui.clock.ClockScreen
import es.pictorario.app.ui.common.MessageDialog
import es.pictorario.app.ui.common.LocalSpeaker
import es.pictorario.app.ui.common.LocalSpeechEnabled
import es.pictorario.app.ui.common.Speaker
import es.pictorario.app.ui.editor.EditorScreen
import es.pictorario.app.ui.home.HomeScreen
import es.pictorario.app.ui.picker.PickerScreen
import es.pictorario.app.ui.settings.SettingsScreen
import es.pictorario.app.ui.theme.PictorarioTheme

/**
 * Hosts every screen.
 *
 * The B4A version swallowed Back everywhere, and so did an earlier draft of this
 * one while the parental lock was on. Back now always navigates: the lock takes
 * away the ability to *edit*, not the ability to move around, and a device whose
 * Back button does nothing is both confusing and at odds with Android.
 */
@Composable
fun AppRoot(state: PictorarioState, speaker: Speaker, onExit: () -> Unit) {
    PictorarioTheme {
        // Since Android 15 apps draw edge to edge by default, so without this
        // the status bar sits on top of the header.
        Surface(Modifier.fillMaxSize().safeDrawingPadding()) {
            if (!state.isReady) return@Surface

            CompositionLocalProvider(
                LocalSpeaker provides speaker,
                LocalSpeechEnabled provides state.settings.speechEnabled,
            ) {
                // What's-new, shown once per update. The original compared the
                // same stored versionCode in Main (pictorario.b4a:154-173).
                if (state.settings.installedVersion != BuildConfig.VERSION_CODE) {
                    MessageDialog(
                        title = "Novedades de esta versión",
                        message = VERSION_CHANGES,
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
}
