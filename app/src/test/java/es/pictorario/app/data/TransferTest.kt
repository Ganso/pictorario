package es.pictorario.app.data

import es.pictorario.app.domain.Activity
import es.pictorario.app.domain.AppData
import es.pictorario.app.domain.DEFAULT_PICTOGRAM_ID
import es.pictorario.app.domain.MAX_ACTIVITIES
import es.pictorario.app.domain.MAX_SEQUENCES
import es.pictorario.app.domain.SampleData
import es.pictorario.app.domain.Sequence
import es.pictorario.app.domain.Settings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TransferTest {

    @Test
    fun aDocumentSurvivesTheRoundTrip() {
        val data = SampleData.initialData()
        assertEquals(data, Transfer.decode(Transfer.encode(data)))
    }

    @Test
    fun truncatedJsonIsNotADocument() {
        assertNull(Transfer.decode(Transfer.encode(SampleData.initialData()).take(80)))
    }

    @Test
    fun jsonFromSomethingElseIsNotADocument() {
        assertNull(Transfer.decode("""{"nombre":"otra cosa","valor":3}"""))
    }

    @Test
    fun aFileWithoutSequencesIsNotADocument() {
        assertNull(Transfer.decode(Transfer.encode(AppData())))
    }

    @Test
    fun aFileWithTooManySequencesIsTrimmedToTheLimit() {
        val many = AppData(sequences = List(30) { sequence("Secuencia $it") })
        assertEquals(MAX_SEQUENCES, Transfer.decode(Transfer.encode(many))?.sequences?.size)
    }

    @Test
    fun aSequenceWithTooManyActivitiesIsTrimmedToTheLimit() {
        val crowded = AppData(
            sequences = listOf(
                sequence("Larga").copy(
                    activities = List(40) { Activity(0, 0, 23, 59, DEFAULT_PICTOGRAM_ID, "x$it") },
                ),
            ),
        )
        val decoded = Transfer.decode(Transfer.encode(crowded))
        assertEquals(MAX_ACTIVITIES, decoded?.sequences?.first()?.activities?.size)
    }

    @Test
    fun activitiesWithImpossibleTimesAreDropped() {
        val broken = AppData(
            sequences = listOf(
                sequence("Rota").copy(
                    activities = listOf(
                        Activity(9, 0, 10, 0, DEFAULT_PICTOGRAM_ID, "buena"),
                        Activity(99, 0, 10, 0, DEFAULT_PICTOGRAM_ID, "hora imposible"),
                        Activity(12, 0, 11, 0, DEFAULT_PICTOGRAM_ID, "acaba antes de empezar"),
                    ),
                ),
            ),
        )
        val activities = Transfer.decode(Transfer.encode(broken))?.sequences?.first()?.activities
        assertEquals(listOf("buena"), activities?.map { it.description })
    }

    @Test
    fun importedActivitiesComeOutSortedAndWithoutOverlaps() {
        val messy = AppData(
            sequences = listOf(
                sequence("Desordenada").copy(
                    activities = listOf(
                        Activity(12, 0, 14, 0, DEFAULT_PICTOGRAM_ID, "tarde"),
                        Activity(9, 0, 13, 0, DEFAULT_PICTOGRAM_ID, "mañana"),
                    ),
                ),
            ),
        )
        val activities = Transfer.decode(Transfer.encode(messy))!!.sequences.first().activities
        assertEquals(listOf("mañana", "tarde"), activities.map { it.description })
        assertEquals(12, activities[0].endHour)
    }

    @Test
    fun replacingKeepsTheSettingsOfThisDevice() {
        val here = AppData(
            sequences = listOf(sequence("De aquí")),
            settings = Settings(appProtected = true, speechEnabled = true),
        )
        val incoming = AppData(sequences = listOf(sequence("De fuera")), settings = Settings())

        val merged = Transfer.merge(here, incoming, replace = true)
        assertEquals(listOf("De fuera"), merged.sequences.map { it.description })
        assertEquals(here.settings, merged.settings)
    }

    @Test
    fun appendingStopsAtTheSequenceLimit() {
        val here = AppData(sequences = List(8) { sequence("Aquí $it") })
        val incoming = AppData(sequences = List(5) { sequence("Fuera $it") })

        val merged = Transfer.merge(here, incoming, replace = false)
        assertEquals(MAX_SEQUENCES, merged.sequences.size)
        assertEquals(3, Transfer.droppedByLimit(here, incoming))
        assertTrue(merged.sequences.take(8).all { it.description.startsWith("Aquí") })
    }

    @Test
    fun nothingIsDroppedWhenEverythingFits() {
        val here = AppData(sequences = listOf(sequence("Aquí")))
        val incoming = AppData(sequences = listOf(sequence("Fuera")))
        assertEquals(0, Transfer.droppedByLimit(here, incoming))
    }

    private fun sequence(description: String) = Sequence(
        description = description,
        activities = listOf(Activity(9, 0, 10, 0, DEFAULT_PICTOGRAM_ID, "algo")),
    )
}
