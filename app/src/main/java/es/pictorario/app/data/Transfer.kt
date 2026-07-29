package es.pictorario.app.data

import es.pictorario.app.domain.Activity
import es.pictorario.app.domain.ActivityRules
import es.pictorario.app.domain.AppData
import es.pictorario.app.domain.MAX_ACTIVITIES
import es.pictorario.app.domain.MAX_SEQUENCES
import es.pictorario.app.domain.Sequence

/**
 * Turns the stored document into a file the adult can keep, and back.
 *
 * The exported file is the very same JSON the app writes to `filesDir`, which
 * means a backup can be inspected, edited by hand or diffed — the same reason
 * the store is readable JSON in the first place. Pictograms are deliberately
 * left out: they are ARASAAC ids, and whatever is missing after an import is
 * downloaded again.
 *
 * Everything here is plain Kotlin so the validation can be tested in the JVM
 * suite; the `Uri` plumbing lives in the caller.
 */
object Transfer {

    fun encode(data: AppData): String =
        AppDataSerializer.json.encodeToString(AppData.serializer(), data)

    /**
     * Reads a file back, or `null` when it is not a Pictorario document.
     *
     * An imported file is not trusted: it may come from a newer version, from a
     * hand edit, or from the wrong file entirely. Anything beyond the app's own
     * limits is trimmed rather than rejected, so a mostly-good backup still
     * restores, but a document with no sequences at all is treated as not ours —
     * importing it could only ever wipe what the user already had.
     */
    fun decode(text: String): AppData? {
        val parsed = runCatching {
            AppDataSerializer.json.decodeFromString(AppData.serializer(), text)
        }.getOrNull() ?: return null

        val sequences = parsed.sequences.take(MAX_SEQUENCES).map(::sanitize)
        if (sequences.isEmpty()) return null
        return parsed.copy(sequences = sequences)
    }

    /**
     * What an import leaves in the store.
     *
     * [Settings][es.pictorario.app.domain.Settings] never travel: hand colours,
     * the parental lock and the read-aloud switch belong to the device they were
     * set on, and carrying the lock across would hand someone else's protection
     * to this user.
     *
     * @param replace true to drop the current sequences, false to append what fits.
     */
    fun merge(current: AppData, imported: AppData, replace: Boolean): AppData {
        val sequences = if (replace) {
            imported.sequences
        } else {
            (current.sequences + imported.sequences).take(MAX_SEQUENCES)
        }
        return current.copy(sequences = sequences)
    }

    /** How many of [imported]'s sequences [merge] would leave out when appending. */
    fun droppedByLimit(current: AppData, imported: AppData): Int =
        (current.sequences.size + imported.sequences.size - MAX_SEQUENCES).coerceAtLeast(0)

    private fun sanitize(sequence: Sequence): Sequence {
        val activities = sequence.activities
            .filter(::isValid)
            .take(MAX_ACTIVITIES)
        return sequence.copy(activities = ActivityRules.normalize(activities))
    }

    private fun isValid(activity: Activity): Boolean =
        activity.startHour in 0..23 &&
            activity.endHour in 0..24 &&
            activity.startMinute in 0..59 &&
            activity.endMinute in 0..59 &&
            activity.startMinutes < activity.endMinutes
}
