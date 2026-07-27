package es.pictorario.app.domain

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

/** A point on the clock face. Kept local so this package stays free of Android. */
data class Point(val x: Float, val y: Float)

/** Inclusive range of whole hours a board covers. */
data class HourRange(val min: Int, val max: Int) {
    /** Never zero, so the arc mapping cannot divide by zero. */
    val span: Int get() = maxOf(1, max - min)
}

/** Radius, as a fraction of the dial radius, at which the activity sectors are drawn. */
const val SECTOR_RADIUS_FRACTION = 0.7f

/** The arc board opens at 120° and sweeps 300°, leaving a gap at the bottom. */
const val ARC_START_DEGREES = 120f
const val ARC_SWEEP_DEGREES = 300f

/**
 * Maps clock times to positions on the dial, and taps back to activities.
 *
 * Ported from the trigonometry scattered through `Visualizacion.bas`
 * (`HoraMinuto_X`/`HoraMinuto_Y` at lines 389-413, sector angles at 315-319,
 * hit testing at 544-560). Compose's canvas shares B4A's conventions — y grows
 * downwards, angles start at three o'clock and run clockwise — so the formulas
 * carry over unchanged.
 *
 * @param center dial centre in pixels.
 * @param radius dial radius in pixels.
 */
data class ClockGeometry(
    val boardType: BoardType,
    val hours: HourRange,
    val center: Point,
    val radius: Float,
) {

    /** Angle in degrees for a time, measured from three o'clock, clockwise. */
    fun angleDegrees(hour: Float, minute: Float): Float {
        val decimalHour = hour + minute / 60f
        return when (boardType) {
            BoardType.FULL_SEQUENCE ->
                ARC_START_DEGREES + (decimalHour - hours.min) * ARC_SWEEP_DEGREES / hours.span

            // A full day around one revolution: half the sweep per hour.
            BoardType.DAY_24H -> decimalHour * 15f + 270f

            else -> decimalHour * 30f + 270f
        }
    }

    /** Point at [distance] (a fraction of [radius]) along the ray for a time. */
    fun pointAt(hour: Float, minute: Float, distance: Float): Point {
        val radians = Math.toRadians(angleDegrees(hour, minute).toDouble())
        return Point(
            x = (cos(radians) * radius * distance + center.x).toFloat(),
            y = (sin(radians) * radius * distance + center.y).toFloat(),
        )
    }

    /** Sector start angle for an activity, normalised to `[0, 360)`. */
    fun startAngle(activity: Activity): Float =
        normalizeDegrees(angleDegrees(activity.startHour.toFloat(), activity.startMinute.toFloat()))

    /**
     * How far the sector sweeps, always positive. The original compared raw
     * start and end angles, which produced a negative or empty sector whenever
     * an activity crossed twelve o'clock on a 12- or 24-hour dial.
     */
    fun sweepAngle(activity: Activity): Float {
        val start = angleDegrees(activity.startHour.toFloat(), activity.startMinute.toFloat())
        val end = angleDegrees(activity.endHour.toFloat(), activity.endMinute.toFloat())
        val sweep = normalizeDegrees(end - start)
        // A full-day activity normalises to zero; draw it as the whole dial.
        return if (sweep == 0f && activity.endMinutes != activity.startMinutes) 360f else sweep
    }

    /** Centre point of a pictogram button, offset in rings of three to avoid overlap. */
    fun buttonCenter(activity: Activity, index: Int): Point {
        val midHour = (activity.startHour + activity.endHour) / 2f
        val midMinute = (activity.startMinute + activity.endMinute) / 2f
        return pointAt(midHour, midMinute, 0.4f + 0.1f * (index % 3))
    }

    /**
     * Which activity a tap landed on, or `null` for a tap outside every sector.
     *
     * Fixes two faults in `PanelAgujas_Touch`: it never checked the distance
     * from the centre, so a tap in a screen corner selected an activity, and its
     * `angle in start..end` test failed for any sector spanning zero degrees.
     */
    fun hitTest(point: Point, activities: List<Activity>): Int? {
        if (hypot(point.x - center.x, point.y - center.y) > radius * SECTOR_RADIUS_FRACTION) {
            return null
        }
        val angle = normalizeDegrees(
            Math.toDegrees(
                atan2((point.y - center.y).toDouble(), (point.x - center.x).toDouble()),
            ).toFloat(),
        )
        val hit = activities.indexOfFirst {
            normalizeDegrees(angle - startAngle(it)) <= sweepAngle(it)
        }
        return hit.takeIf { it >= 0 }
    }

    companion object {

        /** Wraps any angle into `[0, 360)`. */
        fun normalizeDegrees(degrees: Float): Float = ((degrees % 360f) + 360f) % 360f

        /**
         * The hours printed around the dial. Fixed for the clock boards; for the
         * arc, exactly the span the sequence covers, rounded out to whole hours.
         */
        fun dialHours(boardType: BoardType, activities: List<Activity>): HourRange =
            when (boardType) {
                BoardType.MORNING_12H, BoardType.AFTERNOON_12H -> HourRange(1, 12)
                BoardType.DAY_24H -> HourRange(1, 24)
                BoardType.FULL_SEQUENCE -> {
                    val first = activities.firstOrNull()
                    val last = activities.lastOrNull()
                    if (first == null || last == null) {
                        HourRange(0, 24)
                    } else {
                        HourRange(
                            min = first.startHour,
                            max = last.endHour + if (last.endMinute != 0) 1 else 0,
                        )
                    }
                }
            }

        /**
         * The hours during which the hands are drawn at all. Outside this band
         * the current time has no place on the dial, so the original left it
         * blank (`Visualizacion.bas:167-177` and the guard at 263).
         */
        fun handHours(boardType: BoardType, activities: List<Activity>): HourRange =
            when (boardType) {
                BoardType.MORNING_12H -> HourRange(0, 11)
                BoardType.AFTERNOON_12H -> HourRange(12, 23)
                BoardType.DAY_24H -> HourRange(0, 23)
                BoardType.FULL_SEQUENCE -> dialHours(boardType, activities)
            }

        /**
         * Drops activities outside the board's half of the day and clips the
         * ones that straddle noon, as `DibujarActividad` did at line 287.
         * Returns each surviving activity with its original index, so sector
         * colours keep matching the editor's row colours.
         */
        fun visibleActivities(
            boardType: BoardType,
            activities: List<Activity>,
        ): List<IndexedValue<Activity>> = activities.withIndex().mapNotNull { (index, activity) ->
            when (boardType) {
                BoardType.MORNING_12H -> when {
                    activity.startHour >= 12 -> null
                    activity.endHour > 11 -> IndexedValue(
                        index,
                        activity.copy(endHour = 12, endMinute = 0),
                    )
                    else -> IndexedValue(index, activity)
                }

                BoardType.AFTERNOON_12H -> when {
                    activity.endHour <= 11 -> null
                    activity.startHour < 12 -> IndexedValue(
                        index,
                        activity.copy(startHour = 12, startMinute = 0),
                    )
                    else -> IndexedValue(index, activity)
                }

                else -> IndexedValue(index, activity)
            }
        }
    }
}
