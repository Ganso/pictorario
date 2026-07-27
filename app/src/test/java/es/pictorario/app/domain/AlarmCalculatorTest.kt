package es.pictorario.app.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AlarmCalculatorTest {

    @Test
    fun picksTheSoonestUpcomingActivityAcrossSequences() {
        val data = AppData(
            sequences = listOf(
                sequence("Mañana", notifications = true, activity(9, 0), activity(14, 0)),
                sequence("Tarde", notifications = true, activity(11, 0)),
            ),
        )
        val next = AlarmCalculator.next(data, nowMinutes = at(8, 0))!!

        assertEquals(0, next.sequenceIndex)
        assertEquals(0, next.activityIndex)
        assertEquals(9, next.hour)
        assertFalse(next.isTomorrow)
    }

    @Test
    fun sequencesWithNotificationsOffAreIgnored() {
        val data = AppData(
            sequences = listOf(
                sequence("Silenciosa", notifications = false, activity(9, 0)),
                sequence("Con aviso", notifications = true, activity(14, 0)),
            ),
        )
        val next = AlarmCalculator.next(data, nowMinutes = at(8, 0))!!

        assertEquals(1, next.sequenceIndex)
        assertEquals(14, next.hour)
    }

    @Test
    fun theGlobalAlarmSwitchSuppressesEverything() {
        val data = AppData(
            sequences = listOf(sequence("Con aviso", notifications = true, activity(9, 0))),
            settings = Settings(alarmsEnabled = false),
        )
        assertNull(AlarmCalculator.next(data, nowMinutes = at(8, 0)))
    }

    @Test
    fun onceEveryActivityHasPassedTheAlarmRollsOverToTomorrow() {
        val data = AppData(
            sequences = listOf(sequence("Día", notifications = true, activity(9, 0), activity(14, 0))),
        )
        val next = AlarmCalculator.next(data, nowMinutes = at(20, 0))!!

        assertTrue(next.isTomorrow)
        assertEquals(9, next.hour)
        assertEquals(0, next.minute)
    }

    @Test
    fun anActivityStartingExactlyNowIsTreatedAsAlreadyPast() {
        val data = AppData(
            sequences = listOf(sequence("Día", notifications = true, activity(9, 0))),
        )
        val next = AlarmCalculator.next(data, nowMinutes = at(9, 0))!!

        assertTrue(next.isTomorrow)
    }

    @Test
    fun withoutCandidatesThereIsNoAlarm() {
        assertNull(AlarmCalculator.next(AppData(), nowMinutes = at(8, 0)))

        val silent = AppData(
            sequences = listOf(sequence("Silenciosa", notifications = false, activity(9, 0))),
        )
        assertNull(AlarmCalculator.next(silent, nowMinutes = at(8, 0)))
    }

    @Test
    fun minutesArePreserved() {
        val data = AppData(
            sequences = listOf(sequence("Día", notifications = true, activity(8, 45))),
        )
        val next = AlarmCalculator.next(data, nowMinutes = at(8, 0))!!

        assertEquals(8, next.hour)
        assertEquals(45, next.minute)
    }

    private fun at(hour: Int, minute: Int) = hour * 60 + minute

    private fun activity(startHour: Int, startMinute: Int) = Activity(
        startHour = startHour,
        startMinute = startMinute,
        endHour = startHour + 1,
        endMinute = startMinute,
        pictogramId = DEFAULT_PICTOGRAM_ID,
        description = "",
    )

    private fun sequence(
        description: String,
        notifications: Boolean,
        vararg activities: Activity,
    ) = Sequence(
        description = description,
        activities = activities.toList(),
        notifications = notifications,
    )
}
