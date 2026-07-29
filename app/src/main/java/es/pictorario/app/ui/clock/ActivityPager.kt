package es.pictorario.app.ui.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.data.PictogramRepository
import es.pictorario.app.domain.Activity
import es.pictorario.app.domain.Palette
import es.pictorario.app.domain.Sequence
import es.pictorario.app.domain.TimeFormat
import es.pictorario.app.ui.common.PictogramImage
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalTime

/**
 * The activity carousel below the dial, plus the strip of thumbnails that
 * doubles as its page indicator.
 *
 * Replaces `PanelNavigator` from the third-party `sd_panelextra` library, which
 * showed one coloured panel per activity and a row of pictograms to jump
 * between them.
 */
@Composable
fun ActivityPager(
    sequence: Sequence,
    selectedIndex: Int,
    now: LocalTime,
    format24h: Boolean,
    repository: PictogramRepository,
    onSelect: (Int) -> Unit,
    /** Tapping a thumbnail is a deliberate choice; swiping the pager is not. */
    onSelectThumbnail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val activities = sequence.activities
    if (activities.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = selectedIndex.coerceIn(activities.indices),
        pageCount = { activities.size },
    )

    // Tapping the dial scrolls the carousel...
    LaunchedEffect(selectedIndex) {
        if (selectedIndex in activities.indices && selectedIndex != pagerState.currentPage) {
            pagerState.animateScrollToPage(selectedIndex)
        }
    }
    // ...and swiping the carousel selects on the dial.
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }
            .distinctUntilChanged()
            .collect(onSelect)
    }

    Column(modifier) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            ActivityPage(
                activity = activities[page],
                color = Color(Palette.color(page).toInt()),
                now = now,
                format24h = format24h,
            )
        }
        ThumbnailStrip(
            activities = activities,
            selectedIndex = selectedIndex,
            repository = repository,
            onSelect = onSelectThumbnail,
        )
    }
}

@Composable
private fun ActivityPage(
    activity: Activity,
    color: Color,
    now: LocalTime,
    format24h: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxSize().background(color).padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = activity.description,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )

        // The bar only means anything while the activity is under way, so it is
        // left out entirely the rest of the time.
        progressOf(activity, now)?.let { fraction ->
            LinearProgressIndicator(
                progress = { fraction },
                modifier = Modifier.fillMaxWidth(0.8f).height(10.dp),
            )
        }

        Text(
            text = buildAnnotatedString {
                append("desde ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(TimeFormat.time(activity.startHour, activity.startMinute, format24h))
                }
                append("           hasta ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(TimeFormat.time(activity.endHour, activity.endMinute, format24h))
                }
            },
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
    }
}

/** How far through the activity we are, or `null` when it is not running. */
private fun progressOf(activity: Activity, now: LocalTime): Float? {
    val minutes = now.hour * 60 + now.minute
    if (!activity.contains(minutes)) return null
    val span = activity.endMinutes - activity.startMinutes
    if (span <= 0) return null
    return (minutes - activity.startMinutes).toFloat() / span
}

@Composable
private fun ThumbnailStrip(
    activities: List<Activity>,
    selectedIndex: Int,
    repository: PictogramRepository,
    onSelect: (Int) -> Unit,
) {
    // Every activity has to fit: a sequence can hold twenty, so the thumbnails
    // share the width equally rather than taking a fixed size and overflowing.
    Row(
        modifier = Modifier.fillMaxWidth().height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        activities.forEachIndexed { index, activity ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .then(
                        if (index == selectedIndex) {
                            Modifier.clip(CircleShape).background(SelectedThumbnail)
                        } else {
                            Modifier
                        },
                    )
                    .clickable { onSelect(index) },
            ) {
                PictogramImage(
                    pictogramId = activity.pictogramId,
                    repository = repository,
                    size = 56.dp,
                    contentDescription = activity.description,
                    modifier = Modifier.fillMaxSize().padding(2.dp),
                )
            }
        }
    }
}

/** Highlight behind the current thumbnail, matching the original's red marker. */
private val SelectedThumbnail = Color(0x66FF0000)
