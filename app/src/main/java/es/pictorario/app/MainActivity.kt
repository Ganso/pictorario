package es.pictorario.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import es.pictorario.app.alarm.AlarmScheduler
import es.pictorario.app.alarm.Notifications
import es.pictorario.app.ui.AppRoot
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.Screen

/**
 * The single activity hosting every screen. Navigation is a plain state machine
 * rather than a back stack: the app has six screens and no deep links.
 */
class MainActivity : ComponentActivity() {

    private lateinit var state: PictorarioState

    private val requestNotifications =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { /* optional */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Notifications.createChannels(this)

        state = PictorarioState(
            store = pictorario.dataStore,
            pictograms = pictorario.pictograms,
            scope = lifecycleScope,
            // Every write re-arms the alarm, so the schedule can never drift
            // from the stored data — the step Guardar_Configuracion had to
            // remember to call by hand.
            onDataChanged = { AlarmScheduler.reschedule(applicationContext, it) },
        )

        handleAlarmIntent(intent)
        askForNotificationsIfNeeded()

        setContent {
            AppRoot(state = state, onExit = ::finish)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAlarmIntent(intent)
    }

    /** Tapping either notification lands here; open that sequence's board. */
    private fun handleAlarmIntent(intent: Intent?) {
        val sequenceIndex = intent?.getIntExtra(EXTRA_SEQUENCE, -1) ?: -1
        if (sequenceIndex < 0) return
        if (::state.isInitialized) state.navigateTo(Screen.Clock(sequenceIndex))
    }

    /**
     * Notifications are the whole point of the alarms, but the app is perfectly
     * usable without them, so the prompt is a one-off and a refusal is accepted.
     */
    private fun askForNotificationsIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) requestNotifications.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    companion object {
        const val EXTRA_SEQUENCE = "es.pictorario.app.SEQUENCE"
        const val EXTRA_FROM_ALARM = "es.pictorario.app.FROM_ALARM"
    }
}
