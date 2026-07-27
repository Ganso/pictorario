package es.pictorario.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import es.pictorario.app.data.PictogramRepository
import es.pictorario.app.domain.AppData
import es.pictorario.app.domain.BoardType
import es.pictorario.app.domain.MAX_SEQUENCES
import es.pictorario.app.domain.Sequence
import es.pictorario.app.domain.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/** Which screen is on show. Replaces B4A's one-activity-per-screen navigation. */
sealed interface Screen {
    data object Home : Screen
    data class Clock(val sequenceIndex: Int) : Screen
    /** [sequenceIndex] is `null` when creating a sequence rather than editing one. */
    data class Editor(val sequenceIndex: Int?) : Screen
    data object Settings : Screen
    data object About : Screen
}

/**
 * Holds everything the screens read and write.
 *
 * This is the modern stand-in for `Starter.bas`, which B4A implemented as a
 * always-running service holding global mutable state. Here it is a plain object
 * owned by the activity: the data comes from a [DataStore] and every mutation
 * writes the whole document straight back.
 */
class PictorarioState(
    private val store: DataStore<AppData>,
    val pictograms: PictogramRepository,
    private val scope: CoroutineScope,
) {

    var data by mutableStateOf<AppData?>(null)
        private set

    var screen by mutableStateOf<Screen>(Screen.Home)
        private set

    val sequences: List<Sequence> get() = data?.sequences.orEmpty()
    val settings: Settings get() = data?.settings ?: Settings()

    /** True once the stored document has been read and the pictograms are on disk. */
    val isReady: Boolean get() = data != null

    init {
        scope.launch {
            pictograms.seedBundledPictograms()
            store.data.collect { data = it }
        }
    }

    fun navigateTo(target: Screen) {
        screen = target
    }

    fun navigateHome() {
        screen = Screen.Home
    }

    // ---- Mutations -------------------------------------------------------

    private fun edit(transform: (AppData) -> AppData) {
        scope.launch { store.updateData { transform(it) } }
    }

    fun updateSettings(transform: (Settings) -> Settings) =
        edit { it.copy(settings = transform(it.settings)) }

    fun addSequence(sequence: Sequence) = edit { current ->
        if (current.sequences.size >= MAX_SEQUENCES) current
        else current.copy(sequences = current.sequences + sequence)
    }

    fun replaceSequence(index: Int, sequence: Sequence) = edit { current ->
        if (index !in current.sequences.indices) current
        else current.copy(
            sequences = current.sequences.toMutableList().apply { this[index] = sequence },
        )
    }

    fun deleteSequence(index: Int) = edit { current ->
        if (index !in current.sequences.indices) current
        else current.copy(
            sequences = current.sequences.toMutableList().apply { removeAt(index) },
        )
    }

    /** Appends a copy of a sequence, suffixed as the original's "Duplicar" did. */
    fun duplicateSequence(index: Int) = edit { current ->
        val source = current.sequences.getOrNull(index)
        if (source == null || current.sequences.size >= MAX_SEQUENCES) {
            current
        } else {
            val copy = source.copy(description = "${source.description} (copia)")
            current.copy(sequences = current.sequences + copy)
        }
    }

    /**
     * Advances the board through its four styles.
     *
     * `Visualizacion.bas:495` changed this in memory only, so switching to the
     * 24-hour dial and leaving the screen silently reverted it. Here the choice
     * is written back, which is what a user pressing that button expects.
     */
    fun cycleBoardType(index: Int) = edit { current ->
        val sequence = current.sequences.getOrNull(index) ?: return@edit current
        val next = BoardType.entries[(sequence.board.type.ordinal + 1) % BoardType.entries.size]
        current.copy(
            sequences = current.sequences.toMutableList().apply {
                this[index] = sequence.copy(board = sequence.board.copy(type = next))
            },
        )
    }

    /** Restores the example sequences and the bundled pictograms. */
    fun resetEverything() {
        scope.launch {
            pictograms.resetToBundled()
            store.updateData { es.pictorario.app.domain.SampleData.initialData() }
        }
    }

    suspend fun snapshot(): AppData = data ?: store.data.first()
}
