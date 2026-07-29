package es.pictorario.app.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

class AlarmScheduleTest {

    private val madrid = ZoneId.of("Europe/Madrid")

    private fun alarm(minutesFromMidnight: Int) = NextAlarm(0, 0, minutesFromMidnight)

    @Test
    fun anAlarmLaterTodayFiresAtThatTimeToday() {
        val now = ZonedDateTime.of(2026, 7, 29, 9, 0, 0, 0, madrid)
        val fires = AlarmSchedule.triggerAt(now, alarm(17 * 60 + 30)).atZone(madrid)
        assertEquals("2026-07-29T17:30", fires.toLocalDateTime().toString())
    }

    @Test
    fun anAlarmAlreadyPastTodayFiresTomorrow() {
        val now = ZonedDateTime.of(2026, 7, 29, 23, 0, 0, 0, madrid)
        // Así es como AlarmCalculator lo entrega: pasada la hora, suma un día.
        val fires = AlarmSchedule.triggerAt(now, alarm(8 * 60 + NextAlarm.MINUTES_PER_DAY))
        assertEquals("2026-07-30T08:00", fires.atZone(madrid).toLocalDateTime().toString())
    }

    @Test
    fun theZoneComesFromTheDeviceAndNotFromUtc() {
        val minutes = 8 * 60
        val here = AlarmSchedule.triggerAt(
            ZonedDateTime.of(2026, 7, 29, 6, 0, 0, 0, madrid), alarm(minutes))
        val utc = AlarmSchedule.triggerAt(
            ZonedDateTime.of(2026, 7, 29, 6, 0, 0, 0, ZoneId.of("UTC")), alarm(minutes))
        // En julio Madrid va dos horas por delante de UTC: las mismas 08:00 de
        // reloj son dos instantes distintos, y el que vale es el del aparato.
        assertEquals(Duration.ofHours(2), Duration.between(here, utc))
    }

    @Test
    fun crossingIntoSummerTimeDoesNotShiftTheAlarm() {
        // La madrugada del 29 de marzo de 2026 los relojes van de 02:00 a 03:00.
        val now = ZonedDateTime.of(2026, 3, 28, 22, 0, 0, 0, madrid)
        val fires = AlarmSchedule.triggerAt(now, alarm(8 * 60 + NextAlarm.MINUTES_PER_DAY))
        assertEquals("2026-03-29T08:00", fires.atZone(madrid).toLocalDateTime().toString())
    }

    @Test
    fun anAlarmInsideTheHourThatDoesNotExistFiresWhenTheDayResumes() {
        val now = ZonedDateTime.of(2026, 3, 29, 1, 0, 0, 0, madrid)
        // Las 02:30 no existen ese día: el reloj salta de 02:00 a 03:00.
        val fires = AlarmSchedule.triggerAt(now, alarm(2 * 60 + 30)).atZone(madrid)
        assertEquals("2026-03-29T03:00", fires.toLocalDateTime().toString())
        assertTrue(fires.toInstant().isAfter(now.toInstant()))
    }

    @Test
    fun anAlarmInTheRepeatedHourIsNotArmedInThePast() {
        // La madrugada del 25 de octubre de 2026 las 02:00-03:00 pasan dos
        // veces. Estamos en la segunda pasada, y la alarma es a las 02:45.
        val secondPass = ZonedDateTime.ofInstant(
            ZonedDateTime.of(2026, 10, 25, 2, 30, 0, 0, madrid).toInstant()
                .plus(Duration.ofHours(1)),
            madrid,
        )
        assertEquals(2, secondPass.hour)   // sigue marcando las 02:30

        val fires = AlarmSchedule.triggerAt(secondPass, alarm(2 * 60 + 45))
        assertTrue(
            "la alarma no puede quedar armada en el pasado",
            !fires.isBefore(secondPass.toInstant()),
        )
        assertEquals(Duration.ofMinutes(15), Duration.between(secondPass.toInstant(), fires))
    }

    @Test
    fun leavingSummerTimeDoesNotShiftTheAlarm() {
        val now = ZonedDateTime.of(2026, 10, 24, 22, 0, 0, 0, madrid)
        val fires = AlarmSchedule.triggerAt(now, alarm(8 * 60 + NextAlarm.MINUTES_PER_DAY))
        assertEquals("2026-10-25T08:00", fires.atZone(madrid).toLocalDateTime().toString())
    }

    @Test
    fun noAlarmIsEverArmedInThePast() {
        // Barrido de un año entero, cada hora, contra cada hora del día.
        var instant = ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0, madrid)
        val end = ZonedDateTime.of(2027, 1, 1, 0, 0, 0, 0, madrid)
        while (instant.isBefore(end)) {
            for (hour in 0..23) {
                val minutes = hour * 60
                val due = if (minutes <= instant.hour * 60 + instant.minute) {
                    minutes + NextAlarm.MINUTES_PER_DAY
                } else {
                    minutes
                }
                val fires = AlarmSchedule.triggerAt(instant, alarm(due))
                assertTrue(
                    "armada en el pasado: $instant → $fires",
                    !fires.isBefore(instant.toInstant()),
                )
                assertTrue(
                    "armada a más de 48 h vista: $instant → $fires",
                    Duration.between(instant.toInstant(), fires) <= Duration.ofHours(48),
                )
            }
            instant = instant.plusHours(1)
        }
    }
}
