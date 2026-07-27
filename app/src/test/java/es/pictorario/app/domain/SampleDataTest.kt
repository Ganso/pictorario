package es.pictorario.app.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Guards the seed data against drifting away from `Inicializar_Con_Ejemplo`. */
class SampleDataTest {

    private val sequences = SampleData.sequences()

    @Test
    fun thereAreThreeExampleSequencesWithTheOriginalShapes() {
        assertEquals(3, sequences.size)

        assertEquals("Día con clases desde casa", sequences[0].description)
        assertEquals(10, sequences[0].activities.size)
        assertEquals(BoardType.FULL_SEQUENCE, sequences[0].board.type)
        assertEquals(TimeIndicator.HOUR, sequences[0].board.timeIndicator)

        assertEquals("Tarde libre", sequences[1].description)
        assertEquals(6, sequences[1].activities.size)
        assertEquals(BoardType.AFTERNOON_12H, sequences[1].board.type)
        assertEquals(TimeIndicator.HOUR_MINUTE_SECOND, sequences[1].board.timeIndicator)

        assertEquals("Antes de ir al cole", sequences[2].description)
        assertEquals(4, sequences[2].activities.size)
        assertEquals(15, sequences[2].board.iconSizePercent)
    }

    @Test
    fun exampleActivitiesAreOrderedAndFreeOfOverlap() {
        sequences.forEach { sequence ->
            assertEquals(
                "sequence '${sequence.description}'",
                sequence.activities,
                ActivityRules.normalize(sequence.activities),
            )
        }
    }

    @Test
    fun noSequenceExceedsTheLimits() {
        assertTrue(sequences.size <= MAX_SEQUENCES)
        sequences.forEach { assertTrue(it.activities.size <= MAX_ACTIVITIES) }
    }

    @Test
    fun everyPictogramUsedIsOneOfThoseBundledWithTheApp() {
        val bundled = SampleData.BUNDLED_PICTOGRAM_IDS.toSet()
        assertEquals(18, bundled.size)

        sequences.forEach { sequence ->
            assertTrue(
                "sequence pictogram ${sequence.pictogramId}",
                sequence.pictogramId in bundled,
            )
            sequence.activities.forEach {
                assertTrue("activity pictogram ${it.pictogramId}", it.pictogramId in bundled)
            }
        }
        assertTrue(DEFAULT_PICTOGRAM_ID in bundled)
        assertTrue(NEW_ACTIVITY_PICTOGRAM_ID in bundled)
    }

    @Test
    fun alarmsStartEnabledAndTheAppUnlocked() {
        val settings = SampleData.initialData().settings
        assertTrue(settings.alarmsEnabled)
        assertEquals(false, settings.appProtected)
        assertEquals(false, settings.format24h)
    }
}
