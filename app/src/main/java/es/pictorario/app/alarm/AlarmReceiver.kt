package es.pictorario.app.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.pictorario.app.pictorario
import es.pictorario.app.domain.AlarmCalculator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalTime

/**
 * Fired when an activity is due: shows the alarm and arms the next one.
 *
 * A receiver rather than a service. Nothing here runs for long, and dropping the
 * service also drops the foreground-service permission and the service-type
 * declarations Android 14 would otherwise demand.
 */
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_FIRE) return

        val pending = goAsync()
        val appContext = context.applicationContext
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        scope.launch {
            try {
                val data = appContext.pictorario.dataStore.data.first()

                // The alarm that just went off is, by definition, the one due
                // now, so it is looked up against the moment before rescheduling.
                val now = LocalTime.now()
                val due = AlarmCalculator.next(data, now.hour * 60 + now.minute - 1)
                if (due != null && !due.isTomorrow) {
                    Notifications.showAlarm(appContext, data, due, appContext.pictorario.pictograms)
                }
                AlarmScheduler.reschedule(appContext, data)
            } finally {
                pending.finish()
            }
        }
    }

    companion object {
        const val ACTION_FIRE = "es.pictorario.app.ALARM_FIRE"
    }
}
