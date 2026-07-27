package es.pictorario.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import es.pictorario.app.data.PictogramRepository
import es.pictorario.app.data.createAppDataStore
import es.pictorario.app.ui.AppRoot
import es.pictorario.app.ui.PictorarioState

/**
 * The single activity hosting every screen. Navigation is a plain state machine
 * rather than a back stack: the app has six screens and no deep links.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val state = PictorarioState(
            store = createAppDataStore(applicationContext, lifecycleScope),
            pictograms = PictogramRepository(applicationContext),
            scope = lifecycleScope,
        )

        setContent {
            AppRoot(state = state, onExit = ::finish)
        }
    }
}
