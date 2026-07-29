package es.pictorario.app.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import es.pictorario.app.domain.AlarmCalculator
import es.pictorario.app.domain.AlarmSchedule
import es.pictorario.app.domain.AppData
import java.time.ZonedDateTime

/**
 * Keeps exactly one alarm armed: the next activity due, across every sequence
 * with notifications on. When it fires, the receiver re-arms the following one,
 * which is the chain `Starter.CalcularProximaAlarma` set up.
 */
object AlarmScheduler {

    /** Arms the next alarm and refreshes the standing notification. */
    fun reschedule(context: Context, data: AppData) {
        val manager = context.getSystemService(AlarmManager::class.java) ?: return

        manager.cancel(firePendingIntent(context, sequenceIndex = -1, activityIndex = -1))

        val now = ZonedDateTime.now()
        val next = AlarmCalculator.next(data, now.hour * 60 + now.minute)
        Notifications.showUpcoming(context, data, next)
        if (next == null) return

        // La conversión a instante vive en el dominio y está cubierta por
        // tests: es donde una alarma puede quedarse una hora corrida, o armada
        // en el pasado, los dos días del año en que el reloj cambia.
        val triggerAt = AlarmSchedule.triggerAt(now, next).toEpochMilli()

        // Which activity this alarm is for travels inside the intent. Working it
        // out again from the clock when the alarm goes off was the bug that made
        // the alarm silently do nothing: a delay of even one minute pushed the
        // lookup past the start time and the receiver concluded the activity was
        // tomorrow's.
        val pending = firePendingIntent(context, next.sequenceIndex, next.activityIndex)

        if (canScheduleExact(context, manager)) {
            manager.setAlarmClock(
                AlarmManager.AlarmClockInfo(triggerAt, showPendingIntent(context)),
                pending,
            )
        } else {
            // Without the exact-alarm permission the alarm still has to survive
            // Doze, which is precisely when a schedule matters: the device is
            // idle because the child is not using it. setWindow gets no such
            // exemption and could be held back for hours; this one fires, just
            // not to the second.
            manager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pending)
        }
    }

    /**
     * Opens the system screen where exact alarms are granted. Only reachable
     * from a button the adult presses, never on its own.
     */
    fun exactAlarmSettingsIntent(context: Context): Intent? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return null
        return Intent(
            android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
            android.net.Uri.parse("package:${context.packageName}"),
        )
    }

    fun cancel(context: Context) {
        context.getSystemService(AlarmManager::class.java)
            ?.cancel(firePendingIntent(context, sequenceIndex = -1, activityIndex = -1))
    }

    /** Whether the system will honour an exact alarm right now. */
    fun canScheduleExact(context: Context, manager: AlarmManager? = null): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
        val alarms = manager ?: context.getSystemService(AlarmManager::class.java) ?: return false
        return alarms.canScheduleExactAlarms()
    }

    /**
     * The alarm's own intent. Two `PendingIntent`s match when their intents are
     * `filterEquals`, which ignores extras, so the same request code always
     * refers to the one armed alarm and `FLAG_UPDATE_CURRENT` refreshes which
     * activity it points at.
     */
    private fun firePendingIntent(
        context: Context,
        sequenceIndex: Int,
        activityIndex: Int,
    ): PendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, AlarmReceiver::class.java)
            .setAction(AlarmReceiver.ACTION_FIRE)
            .putExtra(AlarmReceiver.EXTRA_SEQUENCE, sequenceIndex)
            .putExtra(AlarmReceiver.EXTRA_ACTIVITY, activityIndex),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    /** Where the status-bar alarm icon takes the user when tapped. */
    private fun showPendingIntent(context: Context): PendingIntent = PendingIntent.getActivity(
        context,
        2,
        Intent(context, es.pictorario.app.MainActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
}
