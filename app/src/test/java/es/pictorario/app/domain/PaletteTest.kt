package es.pictorario.app.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PaletteTest {

    @Test
    fun thereIsOneColourForEveryActivityASequenceCanHold() {
        // Starter.bas:66 declared twenty slots but filled nineteen, so the
        // twentieth activity was painted with a transparent zero.
        assertEquals(MAX_ACTIVITIES, Palette.size)
    }

    @Test
    fun everyColourIsFullyOpaque() {
        repeat(Palette.size) { index ->
            val alpha = (Palette.color(index) ushr 24) and 0xFF
            assertEquals("colour $index", 0xFFL, alpha)
        }
    }

    @Test
    fun coloursAreDistinct() {
        val colors = (0 until Palette.size).map { Palette.color(it) }
        assertEquals(colors.size, colors.toSet().size)
    }

    @Test
    fun indexesBeyondTheEndWrapAround() {
        assertEquals(Palette.color(0), Palette.color(Palette.size))
    }

    @Test
    fun boardAndIndicatorLabelsCoverEveryEnumValue() {
        assertEquals(BoardType.entries.size, BOARD_TYPE_LABELS.size)
        assertEquals(TimeIndicator.entries.size, TIME_INDICATOR_LABELS.size)
        assertTrue(BOARD_TYPE_LABELS.none { it.isBlank() })
    }
}
