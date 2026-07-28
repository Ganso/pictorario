package es.pictorario.app.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import es.pictorario.app.domain.NextAlarm
import es.pictorario.app.pictorario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

        // Which activity is due was decided when the alarm was armed and travels
        // in the intent. Deriving it from the clock here used to make a late
        // alarm show nothing at all.
        val sequenceIndex = intent.getIntExtra(EXTRA_SEQUENCE, -1)
        val activityIndex = intent.getIntExtra(EXTRA_ACTIVITY, -1)

        val pending = goAsync()
        val appContext = context.applicationContext
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

        scope.launch {
            try {
                val data = appContext.pictorario.dataStore.data.first()
                val activity = data.sequences.getOrNull(sequenceIndex)
                    ?.activities?.getOrNull(activityIndex)

                if (activity != null) {
                    Notifications.showAlarm(
                        context = appContext,
                        data = data,
                        alarm = NextAlarm(sequenceIndex, activityIndex, activity.startMinutes),
                        pictograms = appContext.pictorario.pictograms,
                    )
                }
                // Arm the following one whatever happened, so a single stale
                // alarm cannot break the chain.
                AlarmScheduler.reschedule(appContext, data)
            } finally {
                pending.finish()
            }
        }
    }

    companion object {
        const val ACTION_FIRE = "es.pictorario.app.ALARM_FIRE"
        const val EXTRA_SEQUENCE = "es.pictorario.app.ALARM_SEQUENCE"
        const val EXTRA_ACTIVITY = "es.pictorario.app.ALARM_ACTIVITY"
    }
}
