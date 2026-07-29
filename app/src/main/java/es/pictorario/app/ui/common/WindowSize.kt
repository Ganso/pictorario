package es.pictorario.app.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import es.pictorario.app.data.PICTOGRAM_LARGE
import es.pictorario.app.data.PICTOGRAM_SMALL

/**
 * Where the device stops being a phone: 600 dp of shortest side, the same line
 * Android itself draws for the `sw600dp` resource qualifier.
 */
private const val LARGE_SCREEN_DP = 600

/**
 * Widest the reading screens are allowed to get. Past this a form is not easier
 * to use, only harder to scan, and a full-width tablet row leaves the label at
 * one edge and its control at the other.
 */
val READING_WIDTH = 600.dp

/** True on a tablet, in either orientation. Decides how much detail is worth downloading. */
@Composable
fun isLargeScreen(): Boolean =
    LocalConfiguration.current.smallestScreenWidthDp >= LARGE_SCREEN_DP

/**
 * True when the window itself is wide, which a phone held sideways also is.
 * This is what layout decisions hang off: what matters is the room on screen
 * right now, not what kind of device it belongs to.
 */
@Composable
fun isWideWindow(): Boolean = LocalConfiguration.current.screenWidthDp >= LARGE_SCREEN_DP

/**
 * True when the window is wider than it is tall.
 *
 * This is the distinction the layouts actually care about — the clock is a tall
 * shape and has to move beside its carousel rather than above it — so it is
 * asked directly instead of being inferred from a size class.
 */
@Composable
fun isLandscape(): Boolean = LocalConfiguration.current.run { screenWidthDp > screenHeightDp }

/**
 * Caps a screen's content at [READING_WIDTH] and centres it. The narrow phone
 * case is unaffected: the constraint only bites once there is width to spare.
 */
fun Modifier.readableWidth(): Modifier = this
    .fillMaxWidth()
    .wrapContentWidth()
    .widthIn(max = READING_WIDTH)

/** Which ARASAAC size new downloads should ask for on this device. */
@Composable
fun pictogramResolution(): Int = if (isLargeScreen()) PICTOGRAM_LARGE else PICTOGRAM_SMALL
