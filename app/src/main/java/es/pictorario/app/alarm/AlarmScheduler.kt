package es.pictorario.app.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import es.pictorario.app.domain.AlarmCalculator
import es.pictorario.app.domain.AppData
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

/**
 * Keeps exactly one alarm armed: the next activity due, across every sequence
 * with notifications on. When it fires, the receiver re-arms the following one,
 * which is the chain `Starter.CalcularProximaAlarma` set up.
 */
object AlarmScheduler {

    /** Arms the next alarm and refreshes the standing notification. */
    fun reschedule(context: Context, data: AppData) {
        val manager = context.getSystemService(AlarmManager::class.java) ?: return
        val pending = firePendingIntent(context)

        manager.cancel(pending)

        val now = LocalTime.now()
        val next = AlarmCalculator.next(data, now.hour * 60 + now.minute)
        Notifications.showUpcoming(context, data, next)
        if (next == null) return

        val triggerAt = LocalDateTime.now()
            .toLocalDate()
            .plusDays(if (next.isTomorrow) 1 else 0)
            .atTime(next.hour, next.minute)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        // Exact alarms are an optional extra, not a requirement. Without the
        // permission the alarm still fires, just inside a window — which needs
        // no permission at all and keeps the app off Play's restricted list.
        if (canScheduleExact(context, manager)) {
            manager.setAlarmClock(
                AlarmManager.AlarmClockInfo(triggerAt, showPendingIntent(context)),
                pending,
            )
        } else {
            manager.setWindow(AlarmManager.RTC_WAKEUP, triggerAt, INEXACT_WINDOW_MILLIS, pending)
        }
    }

    /**
     * Opens the system screen where exact alarms are granted. Only reachable
     * from a button the adult presses in Settings, never on its own.
     */
    fun exactAlarmSettingsIntent(context: Context): Intent? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return null
        return Intent(
            android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
            android.net.Uri.parse("package:${context.packageName}"),
        )
    }

    fun cancel(context: Context) {
        context.getSystemService(AlarmManager::class.java)?.cancel(firePendingIntent(context))
    }

    /** Whether the system will honour an exact alarm right now. */
    fun canScheduleExact(context: Context, manager: AlarmManager? = null): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
        val alarms = manager ?: context.getSystemService(AlarmManager::class.java) ?: return false
        return alarms.canScheduleExactAlarms()
    }

    private fun firePendingIntent(context: Context): PendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, AlarmReceiver::class.java).setAction(AlarmReceiver.ACTION_FIRE),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    /** Where the status-bar alarm icon takes the user when tapped. */
    private fun showPendingIntent(context: Context): PendingIntent = PendingIntent.getActivity(
        context,
        2,
        Intent(context, es.pictorario.app.MainActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    private const val INEXACT_WINDOW_MILLIS = 5 * 60 * 1000L
}
