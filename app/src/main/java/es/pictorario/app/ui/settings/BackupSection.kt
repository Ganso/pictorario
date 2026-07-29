package es.pictorario.app.ui.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.data.ArasaacApi
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.common.Help
import es.pictorario.app.ui.common.MessageDialog
import es.pictorario.app.ui.common.OptionListDialog
import es.pictorario.app.ui.common.pictogramResolution
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

private const val AUTO_BACKUP_EXPLANATION =
    "Android ya guarda tu configuración en tu cuenta de Google y la restaura al " +
        "instalar Pictorario en un móvil nuevo. Para pasarla entre dos dispositivos " +
        "que ya estés usando, guarda una copia aquí y ábrela en el otro."

/** What the import is doing, so the screen can say so. */
private sealed interface BackupStatus {
    data object Idle : BackupStatus
    data class Downloading(val done: Int, val total: Int) : BackupStatus
}

/**
 * Saving the configuration to a file and reading it back.
 *
 * The Storage Access Framework does the work: the adult picks where the file
 * goes — including Google Drive, which appears in the system chooser like any
 * other place — and the app receives a `Uri` it is already allowed to use. No
 * storage permission is declared, which matters for a listing that was once
 * pulled for policy.
 */
@Composable
fun BackupSection(state: PictorarioState) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val api = remember { ArasaacApi() }
    val resolution = pictogramResolution()

    var status by remember { mutableStateOf<BackupStatus>(BackupStatus.Idle) }
    var result by remember { mutableStateOf<String?>(null) }
    var pendingImport by remember { mutableStateOf<Uri?>(null) }
    var explainAutoBackup by remember { mutableStateOf(false) }

    val export = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json"),
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch {
            val text = state.exportJson()
            val ok = withContext(Dispatchers.IO) {
                runCatching {
                    context.contentResolver.openOutputStream(uri)?.use {
                        it.write(text.encodeToByteArray())
                    } ?: error("sin flujo de salida")
                }.isSuccess
            }
            result = if (ok) {
                "Copia guardada. Ábrela desde el otro dispositivo para recuperar tus secuencias."
            } else {
                "No se pudo escribir el fichero."
            }
        }
    }

    val pick = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument(),
    ) { uri -> pendingImport = uri }

    fun runImport(uri: Uri, replace: Boolean) {
        scope.launch {
            val text = withContext(Dispatchers.IO) {
                runCatching {
                    context.contentResolver.openInputStream(uri)?.use {
                        it.readBytes().decodeToString()
                    }
                }.getOrNull()
            }
            val imported = text?.let { state.importSequences(it, replace) }
            if (imported == null) {
                result = "Ese fichero no es una copia de Pictorario."
                return@launch
            }

            // The file carries ids, not images, so whatever this device has
            // never seen is fetched now. A failure here leaves blank
            // pictograms, which is recoverable; the sequences are already in.
            if (imported.missingPictograms.isNotEmpty()) {
                status = BackupStatus.Downloading(0, imported.missingPictograms.size)
                runCatching {
                    api.downloadMissing(
                        imported.missingPictograms,
                        state.pictograms,
                        resolution,
                    ) { done, total -> status = BackupStatus.Downloading(done, total) }
                }
                status = BackupStatus.Idle
            }

            val stillMissing = imported.missingPictograms.count { !state.pictograms.exists(it) }
            result = buildString {
                append(
                    if (imported.restored == 1) "Se ha recuperado 1 secuencia."
                    else "Se han recuperado ${imported.restored} secuencias.",
                )
                if (imported.dropped > 0) {
                    append(
                        if (imported.dropped == 1) " Una más no cabía y se ha descartado."
                        else " Otras ${imported.dropped} no cabían y se han descartado.",
                    )
                }
                if (stillMissing > 0) {
                    append(
                        if (stillMissing == 1) " Falta un pictograma:" else " Faltan $stillMissing pictogramas:",
                    )
                    append(" conéctate a internet y vuelve a recuperar la copia.")
                }
            }
        }
    }

    Column(Modifier.fillMaxWidth()) {
        Text("Copia de seguridad", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        Help(
            "Guarda todas tus secuencias en un fichero, por ejemplo en Google Drive",
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = { export.launch("pictorario-${LocalDate.now()}.json") },
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(top = 8.dp),
            ) { Text("Guardar copia") }
        }

        Help(
            "Recupera las secuencias de un fichero guardado antes",
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = { pick.launch(arrayOf("application/json")) },
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(top = 8.dp),
            ) { Text("Recuperar copia") }
        }

        Text(
            text = "Cómo funciona la copia automática",
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { explainAutoBackup = true }
                .padding(vertical = 12.dp),
        )

        (status as? BackupStatus.Downloading)?.let { downloading ->
            Text(
                text = "Descargando pictogramas (${downloading.done}/${downloading.total})",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp),
            )
            LinearProgressIndicator(
                progress = {
                    if (downloading.total == 0) 0f
                    else downloading.done.toFloat() / downloading.total
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    pendingImport?.let { uri ->
        OptionListDialog(
            title = "Recuperar copia",
            options = listOf(
                "Reemplazar: borra las secuencias de este dispositivo",
                "Añadir: conserva las de aquí y suma las del fichero",
            ),
            selectedIndex = -1,
            onSelect = { choice ->
                pendingImport = null
                runImport(uri, replace = choice == 0)
            },
            onDismiss = { pendingImport = null },
        )
    }

    if (explainAutoBackup) {
        MessageDialog(
            title = "Copia automática",
            message = AUTO_BACKUP_EXPLANATION,
            onDismiss = { explainAutoBackup = false },
        )
    }

    result?.let { message ->
        MessageDialog(
            title = "Copia de seguridad",
            message = message,
            onDismiss = { result = null },
        )
    }
}
