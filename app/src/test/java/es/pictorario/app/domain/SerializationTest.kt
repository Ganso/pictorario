package es.pictorario.app.domain

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The whole configuration is stored as one JSON document, so these guard the
 * on-disk format: a round trip must be lossless, and a document written by an
 * older version must still load.
 */
class SerializationTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    @Test
    fun theExampleDataSurvivesARoundTrip() {
        val original = SampleData.initialData()
        val restored = json.decodeFromString(
            AppData.serializer(),
            json.encodeToString(AppData.serializer(), original),
        )
        assertEquals(original, restored)
    }

    @Test
    fun enumsAreStoredByNameSoTheirOrderCanChange() {
        val text = json.encodeToString(AppData.serializer(), SampleData.initialData())
        assertTrue(text.contains("\"FULL_SEQUENCE\""))
        assertTrue(text.contains("\"AFTERNOON_12H\""))
    }

    @Test
    fun missingFieldsFallBackToTheirDefaults() {
        val minimal = """{"sequences":[{"description":"Mínima"}]}"""
        val data = json.decodeFromString(AppData.serializer(), minimal)

        val sequence = data.sequences.single()
        assertEquals("Mínima", sequence.description)
        assertEquals(emptyList<Activity>(), sequence.activities)
        assertEquals(DEFAULT_PICTOGRAM_ID, sequence.pictogramId)
        assertEquals(BoardType.FULL_SEQUENCE, sequence.board.type)
        assertEquals(false, sequence.notifications)
        assertEquals(Settings(), data.settings)
    }

    @Test
    fun unknownFieldsFromANewerVersionAreIgnored() {
        val futureDocument = """{"sequences":[],"settings":{},"somethingNew":42}"""
        assertEquals(AppData(), json.decodeFromString(AppData.serializer(), futureDocument))
    }

    @Test
    fun clockColoursSurviveTheirFullAlphaRange() {
        // Stored as Long: as Int, 0xFFFF0000 would wrap negative.
        val settings = Settings(hourColor = 0xFFFF0000, minuteColor = 0xFF00FF00)
        val restored = json.decodeFromString(
            Settings.serializer(),
            json.encodeToString(Settings.serializer(), settings),
        )
        assertEquals(0xFFFF0000, restored.hourColor)
        assertEquals(0xFF00FF00, restored.minuteColor)
    }
}
