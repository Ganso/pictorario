package es.pictorario.app.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.data.ArasaacApi
import es.pictorario.app.data.PICTOGRAM_LARGE
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.common.Help
import es.pictorario.app.ui.common.MessageDialog
import es.pictorario.app.ui.common.isLargeScreen
import kotlinx.coroutines.launch

/**
 * Swaps the pictograms this device already has for the large ARASAAC versions.
 *
 * New downloads pick their size from the screen, so this is only for what came
 * in before — from an older install, from a backup made on a phone, or from the
 * bundled examples. Shown on tablets only: on a phone the large files would be
 * scaled straight back down.
 */
@Composable
fun PictogramQualitySection(state: PictorarioState) {
    if (!isLargeScreen()) return

    val scope = rememberCoroutineScope()
    val api = remember { ArasaacApi() }
    var progress by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var result by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxWidth().padding(top = 8.dp)) {
        Text("Calidad de los pictogramas", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Help(
            "Vuelve a descargar tus pictogramas en alta resolución, para que se " +
                "vean nítidos en esta pantalla",
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                enabled = progress == null,
                onClick = {
                    scope.launch {
                        val ids = state.pictogramIdsInUse()
                        progress = 0 to ids.size
                        val ok = runCatching {
                            api.download(ids, state.pictograms, PICTOGRAM_LARGE) { done, total ->
                                progress = done to total
                            }
                        }.isSuccess
                        progress = null
                        result = if (ok) {
                            "Pictogramas actualizados a alta resolución."
                        } else {
                            "No se pudo conectar con ARASAAC. Tus pictogramas siguen como estaban."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(top = 8.dp),
            ) { Text("Mejorar calidad de los pictogramas") }
        }

        progress?.let { (done, total) ->
            Text("Descargando ($done/$total)", fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
            LinearProgressIndicator(
                progress = { if (total == 0) 0f else done.toFloat() / total },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    result?.let { message ->
        MessageDialog(
            title = "Calidad de los pictogramas",
            message = message,
            onDismiss = { result = null },
        )
    }
}
