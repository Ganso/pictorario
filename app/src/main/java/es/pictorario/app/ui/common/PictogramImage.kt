package es.pictorario.app.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import es.pictorario.app.data.PictogramRepository

/**
 * Draws a pictogram from private storage, decoded no larger than the space it
 * is given. Nothing is shown while it loads or when the file is missing, which
 * matches the original's behaviour of simply leaving the slot blank.
 */
@Composable
fun PictogramImage(
    pictogramId: Int,
    repository: PictogramRepository,
    size: Dp,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val targetPx = with(LocalDensity.current) { size.roundToPx() }
    val bitmap by produceState<ImageBitmap?>(initialValue = null, pictogramId, targetPx) {
        value = repository.load(pictogramId, targetPx)
    }

    Box(modifier) {
        bitmap?.let {
            Image(
                bitmap = it,
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit,
                modifier = Modifier.matchParentSize(),
            )
        }
    }
}
