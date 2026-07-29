package es.pictorario.app.ui.picker

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.R
import es.pictorario.app.data.ArasaacApi
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.common.Help
import es.pictorario.app.ui.common.PictogramImage
import es.pictorario.app.ui.common.pictogramResolution
import es.pictorario.app.ui.theme.FieldBorder
import es.pictorario.app.ui.theme.FieldSurface
import kotlinx.coroutines.launch

/** What the picker is doing right now, so the UI can say so. */
private sealed interface PickerStatus {
    data object Idle : PickerStatus
    data class Searching(val message: String) : PickerStatus
    data class Downloading(val done: Int, val total: Int) : PickerStatus
    data class Failed(val message: String) : PickerStatus
}

/**
 * Picks a pictogram, either from the ones already downloaded or by searching
 * ARASAAC. Port of `SeleccionPictogramas.bas`.
 */
@Composable
fun PickerScreen(state: PictorarioState) {
    val api = remember { ArasaacApi() }
    val scope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current
    val resolution = pictogramResolution()

    var query by remember { mutableStateOf("") }
    var status by remember { mutableStateOf<PickerStatus>(PickerStatus.Idle) }
    var results by remember { mutableStateOf<List<Int>>(emptyList()) }

    // Straight in, the grid shows what is already on the device, newest first —
    // the same welcome the original gave.
    LaunchedEffect(Unit) { results = state.pictograms.downloadedIds() }

    BackHandler { state.applyPickedPictogram(null) }

    fun runSearch() {
        val text = query.trim()
        if (text.isEmpty()) return
        keyboard?.hide()
        scope.launch {
            status = PickerStatus.Searching("Buscando pictogramas…")
            val ids = runCatching { api.search(text) }.getOrElse {
                status = PickerStatus.Failed("No se pudo conectar con ARASAAC")
                return@launch
            }
            if (ids.isEmpty()) {
                status = PickerStatus.Failed("Sin resultados para «$text»")
                results = emptyList()
                return@launch
            }
            status = PickerStatus.Downloading(0, ids.size)
            api.downloadMissing(ids, state.pictograms, resolution) { done, total ->
                status = PickerStatus.Downloading(done, total)
            }
            results = ids.filter(state.pictograms::exists)
            status = PickerStatus.Idle
        }
    }

    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Seleccionar pictograma",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )

        when (val current = status) {
            is PickerStatus.Searching -> StatusLine(current.message)
            is PickerStatus.Downloading -> {
                StatusLine("Descargando imágenes (${current.done}/${current.total})")
                LinearProgressIndicator(
                    progress = { if (current.total == 0) 0f else current.done.toFloat() / current.total },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                )
            }
            is PickerStatus.Failed -> StatusLine(current.message)
            PickerStatus.Idle -> Unit
        }

        LazyVerticalGrid(
            // Three across on a phone; on a tablet the same 120 dp cell simply
            // fits more times, rather than three pictograms stretching to fill.
            columns = GridCells.Adaptive(minSize = 120.dp),
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(results, key = { it }) { id ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(FieldSurface)
                        .border(1.dp, FieldBorder)
                        .clickable { state.applyPickedPictogram(id) },
                ) {
                    PictogramImage(
                        pictogramId = id,
                        repository = state.pictograms,
                        size = 120.dp,
                        contentDescription = "Pictograma $id",
                        modifier = Modifier.fillMaxSize().padding(4.dp),
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Buscar pictograma por texto") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { runSearch() }),
                modifier = Modifier.weight(1f),
            )
            Help("Buscar en ARASAAC y descargar los pictogramas encontrados") {
                Image(
                    painter = painterResource(R.drawable.buscar),
                    contentDescription = "Buscar",
                    modifier = Modifier.size(56.dp).clickable(onClick = ::runSearch),
                )
            }
        }

        Help("Volver sin cambiar el pictograma", modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { state.applyPickedPictogram(null) },
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(horizontal = 8.dp),
            ) {
                Text("Cancelar", fontSize = 18.sp)
            }
        }
    }
}

@Composable
private fun StatusLine(text: String) {
    Text(text, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
}
