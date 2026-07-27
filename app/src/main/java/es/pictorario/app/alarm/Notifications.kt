package es.pictorario.app.alarm

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
 * The replacement is a high-importance notification: it appears as a heads-up
 * with sound and vibration, and opens the board when tapped. A full-screen
 * intent would reproduce the old behaviour more closely, but Play restricts it
 * to alarm and calling apps and this one cannot afford a rejected declaration.
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

        val open = openApp(context, alarm.sequenceIndex, fromAlarm = true)
        val builder = NotificationCompat.Builder(context, CHANNEL_ALARM)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(activity.description)
            .setContentText(sequence.description)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(open)
            // Deliberately NOT a full-screen intent. Google Play reserves that
            // for alarm and calling apps, and a rejected declaration would keep
            // the app off the store. A high-importance heads-up notification
            // that opens the board when tapped conveys the same thing without
            // any restricted permission.

        pictograms.fileFor(activity.pictogramId)
            .takeIf { it.exists() }
            ?.let { BitmapFactory.decodeFile(it.path) }
            ?.let(builder::setLargeIcon)

        runCatching {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ALARM, builder.build())
        }
        context.vibrateOnce()
    }

    private fun openApp(context: Context, sequenceIndex: Int, fromAlarm: Boolean = false) =
        PendingIntent.getActivity(
            context,
            if (fromAlarm) 1 else 0,
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(MainActivity.EXTRA_SEQUENCE, sequenceIndex)
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
