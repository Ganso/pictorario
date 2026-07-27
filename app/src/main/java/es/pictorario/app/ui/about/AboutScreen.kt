package es.pictorario.app.ui.about

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.BuildConfig
import es.pictorario.app.R
import es.pictorario.app.ui.PictorarioState
import es.pictorario.app.ui.common.ConfirmDialog
import es.pictorario.app.ui.common.Help

/** The changelog shown once after an update. Port of `Starter.CambiosVersion`. */
const val VERSION_CHANGES =
    "- Reescritura completa en Kotlin, para poder seguir actualizando la " +
        "aplicación en Google Play.\n\n" +
        "- La configuración se reinicia: los horarios de la versión anterior no " +
        "se conservan.\n\n" +
        "- Podéis notificar cualquier fallo o sugerencia por correo a " +
        "javi@ganso.org.\nGRACIAS"

private const val AUTHOR_URL = "http://www.ganso.org"
private const val ARASAAC_URL = "http://www.arasaac.org"
private const val PROJECT_URL = "http://blog.ganso.org/proyectos/pictorario"
private const val VIDEO_URL = "http://Bit.ly/VideoPictorario"

/** Credits, licences and links. Port of `AcercaDe.bas`. */
@Composable
fun AboutScreen(state: PictorarioState) {
    val context = LocalContext.current
    var changelog by remember { mutableStateOf(false) }

    fun open(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    BackHandler { state.navigateHome() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Help("Abrir la página del proyecto en el navegador") {
            Image(
                painter = painterResource(R.drawable.logotipo),
                contentDescription = "Página del proyecto",
                modifier = Modifier.size(100.dp).clickable { open(PROJECT_URL) },
            )
        }
        Text("Pictorario", fontSize = 36.sp, fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Help("Abrir la web del autor en el navegador") {
                Image(
                    painter = painterResource(R.drawable.trabajar_en_el_ordenador),
                    contentDescription = "Web del autor",
                    modifier = Modifier.size(80.dp).clickable { open(AUTHOR_URL) },
                )
            }
            Text(
                text = labelled("Aplicación: ", "Javier Prieto Martínez (www.ganso.org)") +
                    labelled("\nLicencia: ", "CC (BY-NC-SA)"),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 12.dp),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Help("Abrir la web de ARASAAC en el navegador") {
                Image(
                    painter = painterResource(R.drawable.pictogramas),
                    contentDescription = "Web de ARASAAC",
                    modifier = Modifier.size(80.dp).clickable { open(ARASAAC_URL) },
                )
            }
            Text(
                text = labelled("Pictogramas: ", "Sergio Palao") +
                    labelled("\nProcedencia: ", "ARASAAC (www.arasaac.org)") +
                    labelled("\nLicencia: ", "CC (BY-NC-SA)") +
                    labelled("\nPropiedad: ", "Gobierno de Aragón"),
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 12.dp),
            )
        }

        Text(
            text = "Pulsa los iconos para visitar las páginas",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Para Teo",
            fontFamily = FontFamily(Font(R.font.great_vibes_regular)),
            fontStyle = FontStyle.Italic,
            fontSize = 36.sp,
        )

        Help("Ver las novedades de esta versión") {
            Text(
                text = "Versión ${BuildConfig.VERSION_NAME}",
                fontSize = 14.sp,
                modifier = Modifier.clickable { changelog = true },
            )
        }

        Help("Abrir el vídeo en el navegador", modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { open(VIDEO_URL) },
                modifier = Modifier.fillMaxWidth().height(60.dp),
            ) { Text("Ver el vídeo de presentación") }
        }

        Help("Volver a la portada", modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = state::navigateHome,
                modifier = Modifier.fillMaxWidth().height(60.dp),
            ) { Text("Volver a la portada") }
        }
    }

    if (changelog) {
        ConfirmDialog(
            title = "Novedades de esta versión",
            message = VERSION_CHANGES,
            confirmText = "Aceptar",
            onConfirm = { changelog = false },
            onDismiss = { changelog = false },
        )
    }
}

/** `CSBuilder`'s bold-label-then-value pattern, which the credits lean on. */
private fun labelled(label: String, value: String) = buildAnnotatedString {
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(label) }
    append(value)
}
