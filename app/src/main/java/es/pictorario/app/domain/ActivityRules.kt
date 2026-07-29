package es.pictorario.app.domain

/** Latest an activity may *start*, in minutes since midnight: 23:59. */
const val LAST_MINUTE_OF_DAY = 23 * 60 + 59

/**
 * Latest an activity may *end*: midnight, stored as 24:00.
 *
 * The B4A original capped both ends at 23:59, so «until bedtime» could never be
 * written down exactly and the last minute of the day always fell outside every
 * activity. An end of 24:00 is still the same day — it is not a schedule that
 * crosses midnight, which the model does not represent.
 */
const val END_OF_DAY = 24 * 60

/** Default span given to a newly added activity. */
private const val NEW_ACTIVITY_MINUTES = 30

/** Where the first activity of an empty sequence starts: 08:00. */
private const val FIRST_ACTIVITY_START = 8 * 60

/** What became of a requested time change. */
sealed interface TimeChangeOutcome {

    /** The time was set to exactly what was asked for. */
    data object Applied : TimeChangeOutcome

    /**
     * The requested time could not stand because [clashedWith] was in the way,
     * so it was cut back to where that activity begins or ends.
     */
    data class Rejected(val clashedWith: String) : TimeChangeOutcome

    /** The time was set, but something else had to give. */
    data class Adjusted(val detail: String) : TimeChangeOutcome
}

/** A requested time change, together with what actually happened. */
data class TimeChange(
    val activities: List<Activity>,
    /** Where the edited activity ended up, which reordering may have moved. */
    val editedIndex: Int,
    val outcome: TimeChangeOutcome,
)

/**
 * The scheduling rules the sequence editor enforces, lifted from
 * `ConfigurarSecuencia.bas` and kept free of any Android dependency.
 */
object ActivityRules {

    /** Sorts activities by start time. Ties keep their previous order. */
    fun sortByStart(activities: List<Activity>): List<Activity> =
        activities.sortedBy { it.startMinutes }

    /**
     * Trims any activity that runs into the next one, matching `QuitarSolapes`.
     * Assumes the list is already sorted by start time.
     */
    fun removeOverlaps(activities: List<Activity>): List<Activity> =
        activities.mapIndexed { index, activity ->
            val next = activities.getOrNull(index + 1) ?: return@mapIndexed activity
            if (activity.endMinutes <= next.startMinutes) {
                activity
            } else {
                activity.copy(endHour = next.startHour, endMinute = next.startMinute)
            }
        }

    /** Sorts and then de-overlaps, the pairing `OrdenarActividades` always applied. */
    fun normalize(activities: List<Activity>): List<Activity> =
        removeOverlaps(sortByStart(activities))

    /**
     * Applies a new start time, pushing the end out to start + 30 min when the
     * activity would otherwise end before it begins.
     *
     * `ConfigurarSecuencia.bas:504` compared the new start against
     * `(hora_fin, hora_inicio)` instead of `(hora_fin, minuto_fin)`, so it
     * missed every case where the two hour fields matched — setting 10:45 on an
     * activity ending at 10:50 left it untouched, and the activity kept a
     * negative span. The comparison here uses the real end minute.
     */
    fun withStart(activity: Activity, hour: Int, minute: Int): Activity {
        val moved = activity.copy(startHour = hour, startMinute = minute)
        if (moved.startMinutes < moved.endMinutes) return moved
        val end = minOf(moved.startMinutes + NEW_ACTIVITY_MINUTES, END_OF_DAY)
        return moved.copy(endHour = end / 60, endMinute = end % 60)
    }

    /**
     * Applies a new end time, pulling the start back so the activity keeps a
     * positive span.
     *
     * Midnight is the one time a clock picker cannot tell apart: 00:00 is both
     * the first minute of the day and the last. Asked for as an *end* it can
     * only mean the end, so it is stored as [END_OF_DAY] — which is also what
     * lets an activity run to the very end of the day at all.
     */
    fun withEnd(activity: Activity, hour: Int, minute: Int): Activity {
        val requested = if (hour == 0 && minute == 0) END_OF_DAY else hour * 60 + minute
        val moved = activity.copy(endHour = requested / 60, endMinute = requested % 60)
        if (moved.startMinutes < moved.endMinutes) return moved
        val start = maxOf(moved.endMinutes - NEW_ACTIVITY_MINUTES, 0)
        return moved.copy(startHour = start / 60, startMinute = start % 60)
    }

    /**
     * Moves one end of an activity and reports whether it stuck.
     *
     * The original applied the same sorting and trimming but said nothing about
     * it, so a time the user had just chosen could be quietly overwritten by
     * [removeOverlaps] and they would never know. Here the caller gets an
     * [TimeChangeOutcome] to show.
     *
     * @param isStart which end is being moved.
     */
    fun changeTime(
        activities: List<Activity>,
        index: Int,
        isStart: Boolean,
        hour: Int,
        minute: Int,
    ): TimeChange {
        val original = activities.getOrNull(index)
            ?: return TimeChange(activities, index, TimeChangeOutcome.Applied)

        val moved = if (isStart) {
            withStart(original, hour, minute)
        } else {
            withEnd(original, hour, minute)
        }
        // Read back off the moved activity rather than recomputed, so the
        // midnight translation withEnd applies is taken into account.
        val requested = if (isStart) moved.startMinutes else moved.endMinutes

        // Sorting is tracked by index rather than by value: two activities can
        // hold identical times, so the edited one cannot be found by equality.
        val sorted = activities.toMutableList()
            .also { it[index] = moved }
            .withIndex()
            .sortedBy { it.value.startMinutes }
        val editedIndex = sorted.indexOfFirst { it.index == index }
        val ordered = sorted.map { it.value }
        val trimmed = removeOverlaps(ordered)
        val settled = trimmed[editedIndex]

        val kept = if (isStart) settled.startMinutes == requested else settled.endMinutes == requested
        val neighbour = trimmed.indices.firstOrNull { it != editedIndex && trimmed[it] != ordered[it] }

        val outcome = when {
            // The requested time lost to a neighbour: the one it ran into is
            // whichever activity now bounds it on that side.
            !kept -> TimeChangeOutcome.Rejected(
                clashedWith = trimmed.getOrNull(editedIndex + 1)?.description.orEmpty(),
            )

            neighbour != null -> TimeChangeOutcome.Adjusted(
                detail = "se ha acortado «${trimmed[neighbour].description}» para dejar sitio",
            )

            // withStart and withEnd push the opposite end when the activity
            // would otherwise end before it begins.
            isStart && moved.endMinutes != original.endMinutes ->
                TimeChangeOutcome.Adjusted("la hora de fin se ha movido para que la actividad dure media hora")

            !isStart && moved.startMinutes != original.startMinutes ->
                TimeChangeOutcome.Adjusted("la hora de inicio se ha movido para que la actividad dure media hora")

            editedIndex != index ->
                TimeChangeOutcome.Adjusted("se ha colocado la actividad en su posición correcta")

            else -> TimeChangeOutcome.Applied
        }

        return TimeChange(trimmed, editedIndex, outcome)
    }

    /**
     * Builds the activity that "Añadir Actividad" appends: it starts when the
     * previous one ends (08:00 for the first) and runs for half an hour, capped
     * at 23:59 like `SumarHoras`.
     */
    fun newActivity(existing: List<Activity>): Activity {
        val start = existing.lastOrNull()?.endMinutes ?: FIRST_ACTIVITY_START
        val cappedStart = minOf(start, LAST_MINUTE_OF_DAY)
        val end = minOf(cappedStart + NEW_ACTIVITY_MINUTES, END_OF_DAY)
        return Activity(
            startHour = cappedStart / 60,
            startMinute = cappedStart % 60,
            endHour = end / 60,
            endMinute = end % 60,
            pictogramId = NEW_ACTIVITY_PICTOGRAM_ID,
            description = "",
        )
    }
}
