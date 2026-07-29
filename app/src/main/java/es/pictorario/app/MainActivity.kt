package es.pictorario.app

import android.Manifest
import android.app.KeyguardManager
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
import es.pictorario.app.ui.common.Speaker

/**
 * The single activity hosting every screen. Navigation is a plain state machine
 * rather than a back stack: the app has six screens and no deep links.
 */
class MainActivity : ComponentActivity() {

    private lateinit var state: PictorarioState
    private lateinit var speaker: Speaker

    private val requestNotifications =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { /* optional */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Notifications.createChannels(this)
        speaker = Speaker(this)

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
            AppRoot(state = state, speaker = speaker, onExit = ::finish)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speaker.shutdown()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleAlarmIntent(intent)
    }

    /**
     * Either notification lands here. When it is the alarm, the board has to
     * appear whatever the device was doing: Android has already turned the
     * screen on through the full-screen intent, and this dismisses the keyguard
     * so what shows is the schedule and not the lock screen.
     */
    private fun handleAlarmIntent(intent: Intent?) {
        if (intent == null || !::state.isInitialized) return
        val sequenceIndex = intent.getIntExtra(EXTRA_SEQUENCE, -1)
        if (sequenceIndex < 0) return

        val fromAlarm = intent.getBooleanExtra(EXTRA_FROM_ALARM, false)
        if (!fromAlarm) {
            state.navigateTo(Screen.Clock(sequenceIndex))
            return
        }

        // The alarm has been seen: stop it ringing.
        Notifications.dismissAlarm(this)
        setShowWhenLocked(true)
        setTurnScreenOn(true)
        getSystemService(KeyguardManager::class.java)?.requestDismissKeyguard(this, null)

        val activityIndex = intent.getIntExtra(EXTRA_ACTIVITY, -1)
        state.alarmFired(sequenceIndex, activityIndex)
        // Consumed, so a configuration change does not replay it.
        intent.removeExtra(EXTRA_SEQUENCE)
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
        const val EXTRA_ACTIVITY = "es.pictorario.app.ACTIVITY"
        const val EXTRA_FROM_ALARM = "es.pictorario.app.FROM_ALARM"
    }
}
