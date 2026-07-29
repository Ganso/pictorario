package es.pictorario.app.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import es.pictorario.app.domain.AppData
import es.pictorario.app.domain.SampleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * Persists the whole configuration as one JSON document.
 *
 * The B4A original spread the same data over a `KeyValueStore` keyed by strings
 * like `"ActividadSecuencia.0.3"`, which is exactly the pattern a typed document
 * avoids. The payload is a few kilobytes at most — ten sequences of twenty
 * activities — so rewriting it whole on every change costs nothing, and keeping
 * it as readable JSON means a support case can be diagnosed with `adb pull`.
 */
object AppDataSerializer : Serializer<AppData> {

    /**
     * Also what export and import use, so a backup file is byte for byte the
     * same document the app keeps on disk. See [Transfer].
     */
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    /** A fresh install starts on the example sequences, as `Inicializar_Con_Ejemplo` did. */
    override val defaultValue: AppData = SampleData.initialData()

    override suspend fun readFrom(input: InputStream): AppData =
        try {
            json.decodeFromString(AppData.serializer(), input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            throw CorruptionException("No se pudo leer la configuración", e)
        }

    override suspend fun writeTo(t: AppData, output: OutputStream) {
        output.write(json.encodeToString(AppData.serializer(), t).encodeToByteArray())
    }
}

/** File the configuration lives in, under the app's private storage. */
private const val DATA_FILE = "pictorario.json"

fun createAppDataStore(context: Context, scope: CoroutineScope): DataStore<AppData> =
    DataStoreFactory.create(
        serializer = AppDataSerializer,
        // A truncated or hand-edited file falls back to the examples rather
        // than leaving the app unable to start.
        corruptionHandler = ReplaceFileCorruptionHandler { AppDataSerializer.defaultValue },
        scope = scope,
        produceFile = { File(context.filesDir, DATA_FILE) },
    )
