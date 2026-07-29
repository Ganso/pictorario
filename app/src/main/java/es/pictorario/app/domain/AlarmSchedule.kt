package es.pictorario.app.domain

import java.time.Instant
import java.time.ZonedDateTime

/**
 * Turns «la actividad empieza a las 8:15» into the instant the alarm has to go
 * off.
 *
 * Lives here, apart from [es.pictorario.app.alarm.AlarmScheduler], because it is
 * the part that can go wrong quietly: an hour out, a day out, or armed in the
 * past. `AlarmManager` takes an epoch, and everything the app stores is a time
 * of day with no date and no zone attached to it.
 */
object AlarmSchedule {

    /**
     * When [alarm] is due, given the [now] the schedule was worked out from.
     *
     * The zone comes with [now], so the conversion is always the device's own —
     * never UTC, which would put the alarm off by whatever the offset is.
     *
     * Twice a year a wall clock does not advance one hour per hour, and both
     * cases are handled here rather than left to whatever `java.time` picks:
     *
     * - **Spring.** The hour the clocks jump over does not exist, so an activity
     *   inside it has no instant to fire at. It goes off the moment the day
     *   resumes, which is as close to the intended time as the day allows.
     * - **Autumn.** The hour repeats, and `ZonedDateTime` resolves an ambiguous
     *   time to the *first* of the two. Asked during the second pass, that is an
     *   hour in the past: the alarm would fire the instant it was armed, look
     *   like it had gone off an hour early, and then be gone. The second
     *   occurrence is used when the first has already been and went.
     */
    fun triggerAt(now: ZonedDateTime, alarm: NextAlarm): Instant {
        val days = alarm.minutesFromMidnight / NextAlarm.MINUTES_PER_DAY
        val firstTry = instantOf(now, days.toLong(), alarm)

        // A schedule that lands in the past would fire at once and then be gone,
        // taking the rest of the chain with it. The next day is the only reading
        // left that keeps the activity's time of day.
        return if (firstTry.isBefore(now.toInstant())) {
            instantOf(now, days + 1L, alarm)
        } else {
            firstTry
        }
    }

    private fun instantOf(now: ZonedDateTime, days: Long, alarm: NextAlarm): Instant {
        val local = now.toLocalDate().plusDays(days).atTime(alarm.hour, alarm.minute)
        val rules = now.zone.rules
        val offsets = rules.getValidOffsets(local)

        return when {
            // Skipped over by the spring change: the transition itself is the
            // first instant that exists at or after the requested time.
            offsets.isEmpty() -> rules.getTransition(local).instant

            // Happens twice. Prefer the earlier one, unless it is already past.
            offsets.size > 1 -> {
                val earliest = local.toInstant(offsets[0])
                if (earliest.isBefore(now.toInstant())) local.toInstant(offsets[1]) else earliest
            }

            else -> local.toInstant(offsets[0])
        }
    }
}
