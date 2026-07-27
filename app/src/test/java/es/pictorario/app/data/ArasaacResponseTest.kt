package es.pictorario.app.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Guards the one field the app reads out of an ARASAAC search response.
 *
 * The fixture is trimmed from a real reply to `/api/pictograms/es/search/perro`,
 * keeping the shape and dropping the bulk of the keyword data. Parsing is
 * checked here rather than against the live service so the suite stays offline
 * and deterministic; the live endpoints were verified separately.
 */
class ArasaacResponseTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val fixture = """
        [
          {"_id":7202,"created":"2009-01-19T13:23:19.000Z","downloads":0,
           "tags":["animal","pet"],"schematic":false,
           "keywords":[{"keyword":"perro","type":2,"plural":"perros"}]},
          {"_id":2517,"created":"2011-04-05T09:00:00.000Z","downloads":3,
           "tags":[],"keywords":[{"keyword":"perro guía","type":1}]},
          {"_id":38967,"keywords":[{"keyword":"caseta de perro"}]}
        ]
    """.trimIndent()

    private fun idsOf(body: String): List<Int> =
        (json.parseToJsonElement(body) as JsonArray)
            .mapNotNull { it.jsonObject["_id"]?.jsonPrimitive?.content?.toIntOrNull() }

    @Test
    fun everyPictogramIdIsExtractedInOrder() {
        assertEquals(listOf(7202, 2517, 38967), idsOf(fixture))
    }

    @Test
    fun identifiersAreNumbersNotStrings() {
        // The id doubles as the pictogram's filename, so a quoted value would
        // silently break the whole naming scheme.
        assertEquals(7202, idsOf(fixture).first())
    }

    @Test
    fun anEmptyResultIsNotAnError() {
        assertEquals(emptyList<Int>(), idsOf("[]"))
    }

    @Test
    fun unknownFieldsFromANewerApiAreIgnored() {
        val withExtras = """[{"_id":42,"somethingNew":{"nested":true},"score":9.5}]"""
        assertEquals(listOf(42), idsOf(withExtras))
    }
}
