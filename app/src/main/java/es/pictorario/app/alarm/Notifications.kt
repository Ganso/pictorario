package es.pictorario.app.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import es.pictorario.app.MainActivity
import es.pictorario.app.R
import es.pictorario.app.data.PictogramRepository
import es.pictorario.app.domain.AppData
import es.pictorario.app.domain.NextAlarm
import es.pictorario.app.domain.TimeFormat

/** Quiet, ongoing reminder of what is coming next. */
private const val CHANNEL_UPCOMING = "pictorario.upcoming"

/** The alarm itself: heads-up notification with sound and vibration. */
private const val CHANNEL_ALARM = "pictorario.alarm"

private const val NOTIFICATION_UPCOMING = 1
private const val NOTIFICATION_ALARM = 2

/**
 * Both notifications the app posts, and the channels they need.
 *
 * The B4A version fired its alarm by having a background service call
 * `StartActivity` (`Avisos.bas:26-30`), which Android has blocked since API 29.
 * The replacement is a full-screen intent: on a locked or idle device Android
 * brings the board up itself, and while the device is in use it shows a
 * heads-up instead. The sound repeats until the alarm is acknowledged.
 *
 * A quiet notification was tried first, to keep the app clear of Play's
 * restricted permissions, and it simply did not do the job: the point of this
 * app is that a child who cannot read is shown what comes next, and a silent
 * line in the shade achieves nothing.
 */
object Notifications {

    fun createChannels(context: Context) {
        val manager = context.getSystemService(NotificationManager::class.java) ?: return

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_UPCOMING,
                "Próxima actividad",
                NotificationManager.IMPORTANCE_LOW,
            ).apply {
                description = "Recordatorio discreto de la siguiente actividad."
                setSound(null, null)
                enableVibration(false)
            },
        )

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ALARM,
                "Aviso de actividad",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "Aviso de que empieza una actividad."
                enableVibration(true)
                setSound(
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_ALARM)
                        .build(),
                )
            },
        )
    }

    /** Standing note about the next alarm, or nothing when none is scheduled. */
    fun showUpcoming(context: Context, data: AppData, alarm: NextAlarm?) {
        val manager = NotificationManagerCompat.from(context)
        if (alarm == null) {
            manager.cancel(NOTIFICATION_UPCOMING)
            return
        }
        val sequence = data.sequences.getOrNull(alarm.sequenceIndex) ?: return
        val activity = sequence.activities.getOrNull(alarm.activityIndex) ?: return

        val time = TimeFormat.time(alarm.hour, alarm.minute, data.settings.format24h)
        val tomorrow = if (alarm.isTomorrow) " (mañana)" else ""

        val notification = NotificationCompat.Builder(context, CHANNEL_UPCOMING)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Próxima actividad en Pictorario")
            .setContentText("$time$tomorrow: ${sequence.description} ➞ ${activity.description}")
            .setContentIntent(openApp(context, alarm.sequenceIndex))
            .setOngoing(false)
            .setSilent(true)
            .build()

        runCatching { manager.notify(NOTIFICATION_UPCOMING, notification) }
    }

    /** The alarm going off: vibrate, ring, and offer to open the clock. */
    fun showAlarm(
        context: Context,
        data: AppData,
        alarm: NextAlarm,
        pictograms: PictogramRepository,
    ) {
        val sequence = data.sequences.getOrNull(alarm.sequenceIndex) ?: return
        val activity = sequence.activities.getOrNull(alarm.activityIndex) ?: return

        val open = openApp(context, alarm.sequenceIndex, alarm.activityIndex, fromAlarm = true)
        val builder = NotificationCompat.Builder(context, CHANNEL_ALARM)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(activity.description)
            .setContentText(sequence.description)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setOngoing(true)
            .setContentIntent(open)
            .setFullScreenIntent(open, true)

        pictograms.fileFor(activity.pictogramId)
            .takeIf { it.exists() }
            ?.let { BitmapFactory.decodeFile(it.path) }
            ?.let(builder::setLargeIcon)

        val notification = builder.build().apply {
            // Keeps ringing until the alarm is acknowledged, as the original's
            // Insistent flag did. Without it a single chime is easy to miss.
            flags = flags or Notification.FLAG_INSISTENT
        }

        runCatching {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ALARM, notification)
        }
        context.vibrateOnce()
    }

    /** Silences and clears the alarm once the board is on screen. */
    fun dismissAlarm(context: Context) {
        runCatching { NotificationManagerCompat.from(context).cancel(NOTIFICATION_ALARM) }
    }

    private fun openApp(
        context: Context,
        sequenceIndex: Int,
        activityIndex: Int = -1,
        fromAlarm: Boolean = false,
    ) = PendingIntent.getActivity(
        context,
        if (fromAlarm) 1 else 0,
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(MainActivity.EXTRA_SEQUENCE, sequenceIndex)
            putExtra(MainActivity.EXTRA_ACTIVITY, activityIndex)
            putExtra(MainActivity.EXTRA_FROM_ALARM, fromAlarm)
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
}

/** One second of vibration, as `Visualizacion.AvisoActividad` did. */
private fun Context.vibrateOnce() {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
    vibrator?.vibrate(VibrationEffect.createOneShot(1_000L, VibrationEffect.DEFAULT_AMPLITUDE))
}
