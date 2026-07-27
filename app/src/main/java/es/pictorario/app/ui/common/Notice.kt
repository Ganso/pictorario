package es.pictorario.app.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val NOTICE_MILLIS = 2_200L

/**
 * A brief message overlaid on the screen, the stand-in for B4A's
 * `ToastMessageShow`. Drawn inside the app rather than as a system toast so it
 * stays legible and in place on the board.
 */
class NoticeController(private val scope: CoroutineScope) {

    var text by mutableStateOf<String?>(null)
        private set

    private var hideJob: Job? = null

    fun show(message: String) {
        hideJob?.cancel()
        hideJob = scope.launch {
            text = message
            delay(NOTICE_MILLIS)
            text = null
        }
    }
}

@Composable
fun rememberNotice(): NoticeController {
    val scope = rememberCoroutineScope()
    return remember(scope) { NoticeController(scope) }
}

/** Renders the current message, if any. Place inside the `Box` it should cover. */
@Composable
fun BoxScope.Notice(
    controller: NoticeController,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.BottomCenter,
) {
    val message = controller.text
    AnimatedVisibility(
        visible = message != null,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.align(alignment).then(modifier),
    ) {
        Text(
            text = message.orEmpty(),
            color = Color.White,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xE6303030))
                .padding(horizontal = 20.dp, vertical = 12.dp),
        )
    }
}
