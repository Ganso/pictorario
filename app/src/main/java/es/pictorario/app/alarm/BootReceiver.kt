package es.pictorario.app.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.pictorario.app.pictorario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Re-arms the alarm whenever the system drops it.
 *
 * `ArranqueAutomatico.bas` only listened for `BOOT_COMPLETED`. Three more events
 * clear or invalidate a scheduled alarm just as thoroughly, and the original
 * left the app silently unarmed after any of them: being updated, and the clock
 * or the time zone changing.
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action !in HANDLED_ACTIONS) return

        val pending = goAsync()
        val appContext = context.applicationContext
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        scope.launch {
            try {
                val data = appContext.pictorario.dataStore.data.first()
                Notifications.createChannels(appContext)
                AlarmScheduler.reschedule(appContext, data)
            } finally {
                pending.finish()
            }
        }
    }

    private companion object {
        val HANDLED_ACTIONS = setOf(
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_TIMEZONE_CHANGED,
        )
    }
}
