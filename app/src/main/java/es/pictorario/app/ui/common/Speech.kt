package es.pictorario.app.ui.common

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import java.util.Locale

/**
 * Reads short phrases aloud with the system's text-to-speech engine.
 *
 * The child the app is written for often cannot read, so the pictogram carries
 * the meaning and the voice repeats it. Nothing leaves the device: the engine is
 * whatever Android has installed.
 *
 * Every failure is silent by design. A device without an engine, or without the
 * Spanish voice data, leaves [ready] false and [speak] does nothing — reading
 * aloud is an aid, and no part of the app may depend on it having worked.
 */
class Speaker(context: Context) {

    private var engine: TextToSpeech? = null
    private var ready = false

    init {
        // The engine is assigned before the callback can run, but it is read
        // through the field anyway: the contract does not promise which thread
        // or when.
        engine = TextToSpeech(context.applicationContext) { status ->
            val language = engine?.takeIf { status == TextToSpeech.SUCCESS }?.setLanguage(SPANISH)
            ready = language != null &&
                language != TextToSpeech.LANG_MISSING_DATA &&
                language != TextToSpeech.LANG_NOT_SUPPORTED
        }
    }

    /** Says [text], cutting short whatever was being said before. */
    fun speak(text: String) {
        if (!ready || text.isBlank()) return
        engine?.speak(text, TextToSpeech.QUEUE_FLUSH, null, UTTERANCE_ID)
    }

    fun shutdown() {
        ready = false
        engine?.shutdown()
        engine = null
    }

    private companion object {
        val SPANISH: Locale = Locale.forLanguageTag("es-ES")
        const val UTTERANCE_ID = "pictorario"
    }
}

/** Null while the engine is being built, and in previews. */
val LocalSpeaker = staticCompositionLocalOf<Speaker?> { null }

/** Whether the adult has turned reading aloud on. Follows the stored setting. */
val LocalSpeechEnabled = compositionLocalOf { false }

/**
 * The speaking function every screen uses, already gated by the preference so
 * that no call site has to remember to check it.
 */
@Composable
fun rememberSpeak(): (String) -> Unit {
    val speaker = LocalSpeaker.current
    val enabled = LocalSpeechEnabled.current
    return remember(speaker, enabled) {
        { text -> if (enabled) speaker?.speak(text) }
    }
}

/**
 * Says [text] the moment a finger lands on the control.
 *
 * Only for the two buttons of the board, which is the screen a child uses alone.
 * Reading out every control in every screen was tried and reverted: setting the
 * app up became a running commentary, and the voice stopped meaning "this is
 * what you are looking at" and started meaning nothing in particular.
 *
 * Hung off the *press* rather than the click, and observed in the initial pass
 * without consuming anything, so it neither changes nor delays what the control
 * itself does — a button still fires on release, and a long press still brings
 * up its help.
 */
@Composable
fun Modifier.speakOnTap(text: String): Modifier {
    val speak = rememberSpeak()
    return this.pointerInput(text, speak) {
        awaitEachGesture {
            awaitFirstDown(requireUnconsumed = false, pass = PointerEventPass.Initial)
            speak(text)
        }
    }
}
