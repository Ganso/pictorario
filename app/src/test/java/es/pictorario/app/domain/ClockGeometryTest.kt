package es.pictorario.app.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ClockGeometryTest {

    private val center = Point(100f, 100f)
    private val radius = 100f

    private fun geometry(
        boardType: BoardType,
        hours: HourRange = ClockGeometry.dialHours(boardType, emptyList()),
    ) = ClockGeometry(boardType, hours, center, radius)

    @Test
    fun twelveHourDialPutsNoonAtTheTopAndThreeOClockOnTheRight() {
        val geom = geometry(BoardType.MORNING_12H)
        // 270° is straight up on a canvas whose y axis grows downwards.
        assertEquals(270f, geom.angleDegrees(0f, 0f), TOLERANCE)
        assertEquals(360f, geom.angleDegrees(3f, 0f), TOLERANCE)
        assertEquals(450f, geom.angleDegrees(6f, 0f), TOLERANCE)
    }

    @Test
    fun twentyFourHourDialSweepsHalfAsFastPerHour() {
        val geom = geometry(BoardType.DAY_24H)
        assertEquals(270f, geom.angleDegrees(0f, 0f), TOLERANCE)
        assertEquals(360f, geom.angleDegrees(6f, 0f), TOLERANCE)
        assertEquals(450f, geom.angleDegrees(12f, 0f), TOLERANCE)
    }

    @Test
    fun minutesAdvanceTheAngleProportionally() {
        val geom = geometry(BoardType.MORNING_12H)
        assertEquals(285f, geom.angleDegrees(0f, 30f), TOLERANCE)
    }

    @Test
    fun arcSpansThreeHundredDegreesBetweenItsFirstAndLastHour() {
        val geom = geometry(BoardType.FULL_SEQUENCE, HourRange(8, 9))
        assertEquals(120f, geom.angleDegrees(8f, 0f), TOLERANCE)
        assertEquals(270f, geom.angleDegrees(8f, 30f), TOLERANCE)
        assertEquals(420f, geom.angleDegrees(9f, 0f), TOLERANCE)
    }

    @Test
    fun arcWithASingleHourDoesNotDivideByZero() {
        val geom = geometry(BoardType.FULL_SEQUENCE, HourRange(8, 8))
        assertEquals(120f, geom.angleDegrees(8f, 0f), TOLERANCE)
        assertEquals(420f, geom.angleDegrees(9f, 0f), TOLERANCE)
    }

    @Test
    fun dialHoursOfAnArcCoverExactlyTheSequenceRoundedOutToWholeHours() {
        val exact = listOf(activity(9, 0, 21, 0))
        assertEquals(HourRange(9, 21), ClockGeometry.dialHours(BoardType.FULL_SEQUENCE, exact))

        val ragged = listOf(activity(9, 0, 20, 30))
        assertEquals(HourRange(9, 21), ClockGeometry.dialHours(BoardType.FULL_SEQUENCE, ragged))
    }

    @Test
    fun handsAreConfinedToTheHalfOfTheDayTheBoardShows() {
        assertEquals(HourRange(0, 11), ClockGeometry.handHours(BoardType.MORNING_12H, emptyList()))
        assertEquals(
            HourRange(12, 23),
            ClockGeometry.handHours(BoardType.AFTERNOON_12H, emptyList()),
        )
        assertEquals(HourRange(0, 23), ClockGeometry.handHours(BoardType.DAY_24H, emptyList()))
    }

    @Test
    fun sweepIsAlwaysPositiveEvenWhenAnActivityCrossesTwelve() {
        val geom = geometry(BoardType.DAY_24H)
        // 23:00 to 01:00 wraps past the top of the dial.
        assertEquals(30f, geom.sweepAngle(activity(23, 0, 1, 0)), TOLERANCE)
    }

    @Test
    fun morningBoardDropsAfternoonActivitiesAndClipsTheOneStraddlingNoon() {
        val activities = listOf(
            activity(9, 0, 10, 0),
            activity(11, 0, 14, 0),
            activity(15, 0, 16, 0),
        )
        val visible = ClockGeometry.visibleActivities(BoardType.MORNING_12H, activities)

        assertEquals(listOf(0, 1), visible.map { it.index })
        assertEquals(12, visible[1].value.endHour)
        assertEquals(0, visible[1].value.endMinute)
    }

    @Test
    fun afternoonBoardDropsMorningActivitiesAndClipsTheOneStraddlingNoon() {
        val activities = listOf(
            activity(9, 0, 10, 0),
            activity(11, 0, 14, 0),
            activity(15, 0, 16, 0),
        )
        val visible = ClockGeometry.visibleActivities(BoardType.AFTERNOON_12H, activities)

        assertEquals(listOf(1, 2), visible.map { it.index })
        assertEquals(12, visible[0].value.startHour)
        assertEquals(0, visible[0].value.startMinute)
    }

    @Test
    fun tapInsideASectorSelectsItsActivity() {
        val activities = listOf(activity(8, 0, 8, 30), activity(8, 30, 9, 0))
        val geom = geometry(BoardType.FULL_SEQUENCE, HourRange(8, 9))

        assertEquals(0, geom.hitTest(midpointOf(geom, activities[0]), activities))
        assertEquals(1, geom.hitTest(midpointOf(geom, activities[1]), activities))
    }

    @Test
    fun tapBeyondTheSectorRadiusSelectsNothing() {
        val activities = listOf(activity(8, 0, 9, 0))
        val geom = geometry(BoardType.FULL_SEQUENCE, HourRange(8, 9))
        // A screen corner: the original ignored distance and matched by angle alone.
        assertNull(geom.hitTest(Point(0f, 0f), activities))
    }

    @Test
    fun tapInsideASectorThatSpansZeroDegreesStillSelectsIt() {
        val activities = listOf(activity(23, 0, 1, 0))
        val geom = geometry(BoardType.DAY_24H)
        assertEquals(0, geom.hitTest(midpointOf(geom, activities[0]), activities))
    }

    @Test
    fun pictogramButtonsAlternateDistanceInRingsOfThree() {
        val geom = geometry(BoardType.MORNING_12H)
        val act = activity(3, 0, 3, 0)
        // Straight right of centre, so the distance is readable off the x axis.
        assertEquals(140f, geom.buttonCenter(act, 0).x, TOLERANCE)
        assertEquals(150f, geom.buttonCenter(act, 1).x, TOLERANCE)
        assertEquals(160f, geom.buttonCenter(act, 2).x, TOLERANCE)
        assertEquals(140f, geom.buttonCenter(act, 3).x, TOLERANCE)
    }

    private fun midpointOf(geom: ClockGeometry, act: Activity): Point {
        val angle = geom.startAngle(act) + geom.sweepAngle(act) / 2f
        val radians = Math.toRadians(angle.toDouble())
        val distance = radius * SECTOR_RADIUS_FRACTION / 2f
        return Point(
            (center.x + Math.cos(radians) * distance).toFloat(),
            (center.y + Math.sin(radians) * distance).toFloat(),
        )
    }

    private fun activity(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
    ) = Activity(startHour, startMinute, endHour, endMinute, DEFAULT_PICTOGRAM_ID, "")

    private companion object {
        const val TOLERANCE = 0.01f
    }
}
