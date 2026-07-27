package es.pictorario.app.data

import android.content.Context
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import es.pictorario.app.domain.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Owns the pictogram files. They live as `<arasaacId>.png` under the app's
 * private storage, exactly as in the B4A version, so the numeric ARASAAC id
 * stays the only key the rest of the app needs.
 */
class PictogramRepository(private val context: Context) {

    private val directory = File(context.filesDir, "pictogramas")

    /**
     * Decoded pictograms, capped by pixel footprint rather than entry count:
     * one 2500×2500 asset would otherwise evict everything else on its own.
     */
    private val cache = object : LruCache<Int, ImageBitmap>(CACHE_BYTES) {
        override fun sizeOf(key: Int, value: ImageBitmap): Int = value.width * value.height * 4
    }

    fun fileFor(id: Int): File = File(directory, "$id.png")

    fun exists(id: Int): Boolean = fileFor(id).exists()

    /** Ids already on disk, newest first — the order the picker lists them in. */
    fun downloadedIds(): List<Int> =
        directory.listFiles { file -> file.extension == "png" }
            ?.sortedByDescending { it.lastModified() }
            ?.mapNotNull { it.nameWithoutExtension.toIntOrNull() }
            .orEmpty()

    /**
     * Copies the bundled pictograms out of `assets` on first launch, and
     * restores any that went missing. Port of `CopiarPictogramasIniciales`.
     */
    suspend fun seedBundledPictograms() = withContext(Dispatchers.IO) {
        directory.mkdirs()
        SampleData.BUNDLED_PICTOGRAM_IDS.forEach { id ->
            val target = fileFor(id)
            if (target.exists()) return@forEach
            runCatching {
                context.assets.open("pictogramas/$id.png").use { input ->
                    target.outputStream().use(input::copyTo)
                }
            }
        }
    }

    /**
     * Loads a pictogram scaled down to roughly [targetPx]. Six of the bundled
     * assets are 2500×2500, so decoding them at full size would waste 25 MB
     * each.
     */
    suspend fun load(id: Int, targetPx: Int): ImageBitmap? = withContext(Dispatchers.IO) {
        cache.get(id)?.let { return@withContext it }

        val file = fileFor(id)
        if (!file.exists()) return@withContext null

        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeFile(file.path, bounds)
        if (bounds.outWidth <= 0) return@withContext null

        val options = BitmapFactory.Options().apply {
            inSampleSize = sampleSizeFor(bounds.outWidth, bounds.outHeight, targetPx)
        }
        BitmapFactory.decodeFile(file.path, options)
            ?.asImageBitmap()
            ?.also { cache.put(id, it) }
    }

    /** Deletes every pictogram and re-seeds the bundled ones. Port of `BorrarPictogramas`. */
    suspend fun resetToBundled() = withContext(Dispatchers.IO) {
        cache.evictAll()
        directory.deleteRecursively()
        seedBundledPictograms()
    }

    fun store(id: Int, bytes: ByteArray) {
        directory.mkdirs()
        fileFor(id).writeBytes(bytes)
        cache.remove(id)
    }

    private companion object {
        const val CACHE_BYTES = 24 * 1024 * 1024

        /** Largest power of two that keeps the decoded image at or above [targetPx]. */
        fun sampleSizeFor(width: Int, height: Int, targetPx: Int): Int {
            if (targetPx <= 0) return 1
            var sample = 1
            while (minOf(width, height) / (sample * 2) >= targetPx) sample *= 2
            return sample
        }
    }
}
