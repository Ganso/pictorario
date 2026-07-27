package es.pictorario.app.ui.common

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

/**
 * The parental unlock: a short tap followed by a long press.
 *
 * Deliberately awkward, so a child cannot stumble into it. Each half of the
 * gesture buzzes — 100 ms for the tap that arms it, 300 ms for the long press
 * that completes it — which is the only feedback the user gets that it worked.
 * Ported from `pictorario.b4a:375-391` and `Visualizacion.bas:506-513`.
 */
@Composable
fun Modifier.lockGesture(onUnlock: () -> Unit): Modifier {
    val context = LocalContext.current
    val armed = remember { booleanArrayOf(false) }

    return pointerInput(onUnlock) {
        detectTapGestures(
            onTap = {
                armed[0] = true
                context.vibrate(ARM_MILLIS)
            },
            onLongPress = {
                if (!armed[0]) return@detectTapGestures
                armed[0] = false
                context.vibrate(UNLOCK_MILLIS)
                onUnlock()
            },
        )
    }
}

private const val ARM_MILLIS = 100L
private const val UNLOCK_MILLIS = 300L

private fun Context.vibrate(millis: Long) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
    vibrator?.vibrate(VibrationEffect.createOneShot(millis, VibrationEffect.DEFAULT_AMPLITUDE))
}
