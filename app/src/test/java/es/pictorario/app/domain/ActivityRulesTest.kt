package es.pictorario.app.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ActivityRulesTest {

    @Test
    fun activitiesAreOrderedByStartTime() {
        val sorted = ActivityRules.sortByStart(
            listOf(activity(15, 0, 16, 0), activity(9, 0, 10, 0), activity(12, 30, 13, 0)),
        )
        assertEquals(listOf(9, 12, 15), sorted.map { it.startHour })
    }

    @Test
    fun anActivityRunningIntoTheNextOneIsTrimmedBackToItsStart() {
        val trimmed = ActivityRules.removeOverlaps(
            listOf(activity(9, 0, 11, 0), activity(10, 0, 12, 0)),
        )
        assertEquals(10, trimmed[0].endHour)
        assertEquals(0, trimmed[0].endMinute)
        assertEquals(10, trimmed[1].startHour)
    }

    @Test
    fun activitiesThatMerelyTouchAreLeftAlone() {
        val original = listOf(activity(9, 0, 10, 0), activity(10, 0, 11, 0))
        assertEquals(original, ActivityRules.removeOverlaps(original))
    }

    @Test
    fun movingAStartPastItsEndPushesTheEndOutByHalfAnHour() {
        val moved = ActivityRules.withStart(activity(9, 0, 10, 0), 11, 0)
        assertEquals(11, moved.startHour)
        assertEquals(11, moved.endHour)
        assertEquals(30, moved.endMinute)
    }

    @Test
    fun movingAStartPastAnEndInTheSameHourIsAlsoDetected() {
        // Regression for ConfigurarSecuencia.bas:504, which compared the new
        // start against (endHour, startHour) instead of (endHour, endMinute).
        // Both hours being 10, the original saw no conflict and left the
        // activity ending five minutes before it began.
        val moved = ActivityRules.withStart(activity(10, 0, 10, 50), 10, 55)
        assertEquals(10, moved.startHour)
        assertEquals(55, moved.startMinute)
        assertEquals(11, moved.endHour)
        assertEquals(25, moved.endMinute)
    }

    @Test
    fun aStartThatStillPrecedesItsEndIsAppliedUntouched() {
        val moved = ActivityRules.withStart(activity(10, 0, 12, 0), 11, 15)
        assertEquals(11, moved.startHour)
        assertEquals(15, moved.startMinute)
        assertEquals(12, moved.endHour)
        assertEquals(0, moved.endMinute)
    }

    @Test
    fun movingAnEndBeforeItsStartPullsTheStartBack() {
        val moved = ActivityRules.withEnd(activity(10, 0, 12, 0), 9, 0)
        assertEquals(8, moved.startHour)
        assertEquals(30, moved.startMinute)
        assertEquals(9, moved.endHour)
    }

    @Test
    fun theFirstActivityOfAnEmptySequenceStartsAtEight() {
        val added = ActivityRules.newActivity(emptyList())
        assertEquals(8, added.startHour)
        assertEquals(0, added.startMinute)
        assertEquals(8, added.endHour)
        assertEquals(30, added.endMinute)
        assertEquals(NEW_ACTIVITY_PICTOGRAM_ID, added.pictogramId)
    }

    @Test
    fun aNewActivityStartsWhereThePreviousOneEnded() {
        val added = ActivityRules.newActivity(listOf(activity(9, 0, 10, 45)))
        assertEquals(10, added.startHour)
        assertEquals(45, added.startMinute)
        assertEquals(11, added.endHour)
        assertEquals(15, added.endMinute)
    }

    @Test
    fun aNewActivityNeverRunsPastTheEndOfTheDay() {
        val added = ActivityRules.newActivity(listOf(activity(23, 0, 23, 50)))
        assertEquals(END_OF_DAY, added.endHour * 60 + added.endMinute)
    }

    @Test
    fun midnightAsAnEndMeansTheEndOfTheDay() {
        val moved = ActivityRules.withEnd(activity(22, 0, 23, 0), 0, 0)
        assertEquals(24, moved.endHour)
        assertEquals(0, moved.endMinute)
        assertEquals(END_OF_DAY, moved.endMinutes)
    }

    @Test
    fun midnightAsAnEndDoesNotDragTheStartBackwards() {
        val moved = ActivityRules.withEnd(activity(22, 0, 23, 0), 0, 0)
        assertEquals(22, moved.startHour)
    }

    @Test
    fun anActivityEndingAtMidnightCoversTheLastMinuteOfTheDay() {
        assertTrue(ActivityRules.withEnd(activity(22, 0, 23, 0), 0, 0).contains(23 * 60 + 59))
    }

    @Test
    fun anEndOfMidnightIsReportedAsApplied() {
        val change = ActivityRules.changeTime(
            listOf(activity(22, 0, 23, 0)),
            index = 0,
            isStart = false,
            hour = 0,
            minute = 0,
        )
        assertEquals(TimeChangeOutcome.Applied, change.outcome)
        assertEquals(END_OF_DAY, change.activities[0].endMinutes)
    }

    @Test
    fun anEndOfMidnightIsNotTrimmedByAnActivityThatStartsEarlier() {
        val normalized = ActivityRules.normalize(
            listOf(activity(22, 0, 24, 0), activity(9, 0, 10, 0)),
        )
        assertEquals(listOf(9, 22), normalized.map { it.startHour })
        assertEquals(END_OF_DAY, normalized[1].endMinutes)
    }

    @Test
    fun normalizeSortsAndThenTrims() {
        val result = ActivityRules.normalize(
            listOf(activity(10, 0, 12, 0), activity(9, 0, 11, 0)),
        )
        assertEquals(listOf(9, 10), result.map { it.startHour })
        assertEquals(10, result[0].endHour)
    }

    // ---- changeTime: what the user is told -------------------------------

    @Test
    fun aTimeThatFitsIsAppliedWithoutComment() {
        val result = ActivityRules.changeTime(
            activities = listOf(named("Desayunar", 9, 0, 10, 0), named("Cole", 11, 0, 12, 0)),
            index = 0,
            isStart = false,
            hour = 10,
            minute = 30,
        )
        assertEquals(TimeChangeOutcome.Applied, result.outcome)
        assertEquals(10, result.activities[0].endHour)
        assertEquals(30, result.activities[0].endMinute)
    }

    @Test
    fun anEndThatRunsIntoTheNextActivityIsRejectedAndNamesIt() {
        val result = ActivityRules.changeTime(
            activities = listOf(named("Desayunar", 9, 0, 10, 0), named("Cole", 11, 0, 12, 0)),
            index = 0,
            isStart = false,
            hour = 11,
            minute = 30,
        )
        assertEquals(TimeChangeOutcome.Rejected("Cole"), result.outcome)
        // Cut back to where the next one starts, never left overlapping.
        assertEquals(11, result.activities[0].endHour)
        assertEquals(0, result.activities[0].endMinute)
    }

    @Test
    fun aStartThatEatsIntoThePreviousActivityShortensIt() {
        val result = ActivityRules.changeTime(
            activities = listOf(named("Desayunar", 9, 0, 10, 0), named("Cole", 11, 0, 12, 0)),
            index = 1,
            isStart = true,
            hour = 9,
            minute = 30,
        )
        val outcome = result.outcome
        assertTrue("era $outcome", outcome is TimeChangeOutcome.Adjusted)
        assertTrue((outcome as TimeChangeOutcome.Adjusted).detail.contains("Desayunar"))
        // The requested start does stand; the previous activity gives way.
        assertEquals(9, result.activities[1].startHour)
        assertEquals(30, result.activities[1].startMinute)
        assertEquals(9, result.activities[0].endHour)
        assertEquals(30, result.activities[0].endMinute)
    }

    @Test
    fun movingAStartPastItsOwnEndIsReportedAsAnAdjustment() {
        val result = ActivityRules.changeTime(
            activities = listOf(named("Desayunar", 9, 0, 10, 0)),
            index = 0,
            isStart = true,
            hour = 11,
            minute = 0,
        )
        val outcome = result.outcome
        assertTrue("era $outcome", outcome is TimeChangeOutcome.Adjusted)
        assertTrue((outcome as TimeChangeOutcome.Adjusted).detail.contains("fin"))
        assertEquals(11, result.activities[0].endHour)
        assertEquals(30, result.activities[0].endMinute)
    }

    @Test
    fun anActivityMovedPastAnotherIsReorderedAndTheNewPositionIsReported() {
        val result = ActivityRules.changeTime(
            activities = listOf(named("Desayunar", 9, 0, 10, 0), named("Cole", 11, 0, 12, 0)),
            index = 0,
            isStart = true,
            hour = 13,
            minute = 0,
        )
        assertEquals(1, result.editedIndex)
        assertEquals("Cole", result.activities[0].description)
        assertEquals("Desayunar", result.activities[1].description)
    }

    @Test
    fun theEditedActivityIsTrackedByPositionNotByValue() {
        // Two activities with identical times: finding the edited one by
        // equality would pick the wrong one.
        val twins = listOf(named("Uno", 9, 0, 10, 0), named("Dos", 9, 0, 10, 0))
        val result = ActivityRules.changeTime(twins, index = 1, isStart = false, hour = 9, minute = 30)
        assertEquals("Dos", result.activities[result.editedIndex].description)
    }

    private fun named(
        description: String,
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
    ) = Activity(startHour, startMinute, endHour, endMinute, DEFAULT_PICTOGRAM_ID, description)

    private fun activity(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
    ) = Activity(startHour, startMinute, endHour, endMinute, DEFAULT_PICTOGRAM_ID, "")
}
