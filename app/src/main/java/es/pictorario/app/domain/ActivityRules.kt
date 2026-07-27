package es.pictorario.app.domain

/** Last representable instant of a day, in minutes since midnight: 23:59. */
const val LAST_MINUTE_OF_DAY = 23 * 60 + 59

/** Default span given to a newly added activity. */
private const val NEW_ACTIVITY_MINUTES = 30

/** Where the first activity of an empty sequence starts: 08:00. */
private const val FIRST_ACTIVITY_START = 8 * 60

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
        val end = minOf(moved.startMinutes + NEW_ACTIVITY_MINUTES, LAST_MINUTE_OF_DAY)
        return moved.copy(endHour = end / 60, endMinute = end % 60)
    }

    /**
     * Applies a new end time, pulling the start back so the activity keeps a
     * positive span.
     */
    fun withEnd(activity: Activity, hour: Int, minute: Int): Activity {
        val moved = activity.copy(endHour = hour, endMinute = minute)
        if (moved.startMinutes < moved.endMinutes) return moved
        val start = maxOf(moved.endMinutes - NEW_ACTIVITY_MINUTES, 0)
        return moved.copy(startHour = start / 60, startMinute = start % 60)
    }

    /**
     * Builds the activity that "Añadir Actividad" appends: it starts when the
     * previous one ends (08:00 for the first) and runs for half an hour, capped
     * at 23:59 like `SumarHoras`.
     */
    fun newActivity(existing: List<Activity>): Activity {
        val start = existing.lastOrNull()?.endMinutes ?: FIRST_ACTIVITY_START
        val cappedStart = minOf(start, LAST_MINUTE_OF_DAY)
        val end = minOf(cappedStart + NEW_ACTIVITY_MINUTES, LAST_MINUTE_OF_DAY)
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
