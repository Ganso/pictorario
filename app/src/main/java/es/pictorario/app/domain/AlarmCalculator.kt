package es.pictorario.app.domain

/** The activity the next alarm will fire for. */
data class NextAlarm(
    val sequenceIndex: Int,
    val activityIndex: Int,
    /**
     * Minutes from midnight *today* to the alarm. Values of 1440 or more mean it
     * fires tomorrow.
     */
    val minutesFromMidnight: Int,
) {
    val isTomorrow: Boolean get() = minutesFromMidnight >= MINUTES_PER_DAY
    val hour: Int get() = (minutesFromMidnight % MINUTES_PER_DAY) / 60
    val minute: Int get() = minutesFromMidnight % 60

    companion object {
        const val MINUTES_PER_DAY = 24 * 60
    }
}

/**
 * Picks the activity whose start time comes soonest, across every sequence that
 * has notifications on. Port of `Starter.CalcularProximaAlarma`, with the clock
 * passed in so the result is deterministic and testable.
 */
object AlarmCalculator {

    /**
     * @param nowMinutes minutes since midnight right now.
     * @return the next alarm, or `null` when nothing is scheduled.
     *
     * An activity starting exactly now is treated as already past and rolls over
     * to tomorrow, which is what the original's `<=` comparison did.
     */
    fun next(data: AppData, nowMinutes: Int): NextAlarm? {
        if (!data.settings.alarmsEnabled) return null

        return data.sequences.withIndex()
            .filter { (_, sequence) -> sequence.notifications }
            .flatMap { (sequenceIndex, sequence) ->
                sequence.activities.mapIndexed { activityIndex, activity ->
                    val start = activity.startMinutes
                    val due = if (start <= nowMinutes) start + NextAlarm.MINUTES_PER_DAY else start
                    NextAlarm(sequenceIndex, activityIndex, due)
                }
            }
            .minByOrNull { it.minutesFromMidnight }
    }
}
