package es.pictorario.app.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeFormatTest {

    @Test
    fun afternoonTimesAreHalvedAndMarkedInTwelveHourMode() {
        assertEquals("01:05 p.m.", TimeFormat.time(13, 5, format24h = false))
        assertEquals("13:05", TimeFormat.time(13, 5, format24h = true))
    }

    @Test
    fun morningTimesKeepTheirHour() {
        assertEquals("09:30 a.m.", TimeFormat.time(9, 30, format24h = false))
        assertEquals("09:30", TimeFormat.time(9, 30, format24h = true))
    }

    @Test
    fun midnightAndNoonReadAsTwelve() {
        // The original printed both as 00:00 because it never mapped a zeroed
        // hour back up to twelve.
        assertEquals("12:00 a.m.", TimeFormat.time(0, 0, format24h = false))
        assertEquals("12:00 p.m.", TimeFormat.time(12, 0, format24h = false))
        assertEquals("00:00", TimeFormat.time(0, 0, format24h = true))
    }

    @Test
    fun minutesArePaddedToTwoDigits() {
        assertEquals("09:05 a.m.", TimeFormat.time(9, 5, format24h = false))
    }

    @Test
    fun dialNumbersRunFromOneToTwelve() {
        assertEquals(12, TimeFormat.displayHour(0, format24h = false))
        assertEquals(1, TimeFormat.displayHour(13, format24h = false))
        assertEquals(12, TimeFormat.displayHour(12, format24h = false))
        assertEquals(11, TimeFormat.displayHour(23, format24h = false))
        assertEquals(23, TimeFormat.displayHour(23, format24h = true))
    }

    @Test
    fun theDigitalReadoutNamesMidnightAndNoon() {
        assertEquals("12:00 de la noche", TimeFormat.digitalClock(0, 0, format24h = false))
        assertEquals("12:30 del mediodía", TimeFormat.digitalClock(12, 30, format24h = false))
        assertEquals("05:22 p.m.", TimeFormat.digitalClock(17, 22, format24h = false))
        assertEquals("09:15 a.m.", TimeFormat.digitalClock(9, 15, format24h = false))
        assertEquals("17:22", TimeFormat.digitalClock(17, 22, format24h = true))
    }
}
