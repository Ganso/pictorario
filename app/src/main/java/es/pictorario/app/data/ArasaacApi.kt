package es.pictorario.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * The two sizes ARASAAC is asked for. Phones take the small one — anything more
 * is invisible on a 60 dp button and costs storage and decoding time — and
 * tablets the large one, where a pictogram can fill a third of the screen.
 */
const val PICTOGRAM_SMALL = 500
const val PICTOGRAM_LARGE = 2500

/**
 * The two ARASAAC endpoints the app needs. No key, no token, no pagination —
 * verified against the live service, unchanged since the B4A version.
 *
 * `HttpURLConnection` rather than a client library: two calls without auth do
 * not justify pulling in Retrofit or Ktor.
 */
class ArasaacApi(private val language: String = "es") {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Pictogram ids matching [text]. The response carries a great deal more per
     * entry, but the id is the only field the app has ever used.
     */
    suspend fun search(text: String): List<Int> = withContext(Dispatchers.IO) {
        val query = URLEncoder.encode(text, "UTF-8").replace("+", "%20")
        val body = URL("$API_BASE/pictograms/$language/search/$query").readTextOrThrow()
        json.parseToJsonElement(body)
            .let { it as? kotlinx.serialization.json.JsonArray ?: return@withContext emptyList() }
            .mapNotNull { it.jsonObject["_id"]?.jsonPrimitive?.content?.toIntOrNull() }
    }

    /**
     * Downloads whichever of [ids] is not already on disk.
     *
     * The B4A version fetched them one after another, and
     * `SeleccionPictogramas.bas:117` records the contortions its author went
     * through to make even that work. Here they go in parallel, capped so the
     * server is not hit with sixty simultaneous requests.
     */
    suspend fun downloadMissing(
        ids: List<Int>,
        repository: PictogramRepository,
        resolution: Int = PICTOGRAM_SMALL,
        onProgress: (done: Int, total: Int) -> Unit = { _, _ -> },
    ): Int = download(ids.filterNot(repository::exists), repository, resolution, onProgress)

    /**
     * Fetches [ids] whether or not they are already on disk, which is how a
     * device swaps its small pictograms for large ones.
     *
     * Each file is only replaced once its download succeeds, so losing the
     * connection half way through leaves the previous images in place rather
     * than a schedule full of blanks.
     */
    suspend fun download(
        ids: List<Int>,
        repository: PictogramRepository,
        resolution: Int = PICTOGRAM_SMALL,
        onProgress: (done: Int, total: Int) -> Unit = { _, _ -> },
    ): Int = coroutineScope {
        if (ids.isEmpty()) return@coroutineScope 0

        val gate = Semaphore(MAX_PARALLEL_DOWNLOADS)
        var done = 0
        ids.map { id ->
            async(Dispatchers.IO) {
                gate.withPermit {
                    runCatching {
                        val url = "$STATIC_BASE/pictograms/$id/${id}_$resolution.png"
                        repository.store(id, URL(url).readBytesOrThrow())
                    }
                    synchronized(gate) { onProgress(++done, ids.size) }
                }
            }
        }.awaitAll()
        ids.size
    }

    private fun URL.readTextOrThrow(): String =
        openConnectionChecked().use { it.inputStream.readBytes().decodeToString() }

    private fun URL.readBytesOrThrow(): ByteArray =
        openConnectionChecked().use { it.inputStream.readBytes() }

    private fun URL.openConnectionChecked(): HttpURLConnection =
        (openConnection() as HttpURLConnection).apply {
            connectTimeout = TIMEOUT_MILLIS
            readTimeout = TIMEOUT_MILLIS
            if (responseCode !in 200..299) {
                disconnect()
                error("ARASAAC respondió $responseCode")
            }
        }

    private inline fun <T> HttpURLConnection.use(block: (HttpURLConnection) -> T): T =
        try {
            block(this)
        } finally {
            disconnect()
        }

    private companion object {
        const val API_BASE = "https://api.arasaac.org/api"
        const val STATIC_BASE = "https://static.arasaac.org"
        const val MAX_PARALLEL_DOWNLOADS = 6
        const val TIMEOUT_MILLIS = 15_000
    }
}
