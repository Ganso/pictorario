package es.pictorario.app.ui.about

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import es.pictorario.app.ui.common.MessageDialog
import es.pictorario.app.ui.common.Help
import es.pictorario.app.ui.common.readableWidth

/** The changelog shown once after an update. Port of `Starter.CambiosVersion`. */
/**
 * What is new, shown once per update and from the version number here.
 *
 * Kept under Google Play's 500-character limit for the *What's new* field, so
 * the same text serves both places and cannot drift apart. See CHANGELOG.md for
 * the full account.
 */
const val VERSION_CHANGES =
    "Pictorario vuelve, reescrito por completo para funcionar en los Android " +
        "de hoy.\n\n" +
        "• Lectura en voz alta de las actividades.\n" +
        "• Copia de seguridad: guarda tus horarios y recupéralos en otro " +
        "móvil.\n" +
        "• El aviso abre el horario a pantalla completa al empezar una " +
        "actividad.\n" +
        "• Diseño para tablet y para pantalla horizontal.\n" +
        "• Mantén pulsado cualquier botón para ver qué hace.\n" +
        "• Corregidos errores del reloj y de los horarios.\n\n" +
        "Fallos y sugerencias: javi@ganso.org"

private const val AUTHOR_URL = "http://www.ganso.org"
private const val ARASAAC_URL = "http://www.arasaac.org"
private const val PROJECT_URL = "http://blog.ganso.org/proyectos/pictorario"
private const val VIDEO_URL = "https://www.youtube.com/watch?v=cjTAGguz5H0"
private const val PRIVACY_URL = "https://ganso.org/pictorarioprivacy.html"

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
            .readableWidth()
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

        Help("Abrir la política de privacidad en el navegador", modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { open(PRIVACY_URL) },
                modifier = Modifier.fillMaxWidth().height(60.dp),
            ) { Text("Política de privacidad") }
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
        MessageDialog(
            title = "Novedades de esta versión",
            message = VERSION_CHANGES,
            onDismiss = { changelog = false },
        )
    }
}

/** `CSBuilder`'s bold-label-then-value pattern, which the credits lean on. */
private fun labelled(label: String, value: String) = buildAnnotatedString {
    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(label) }
    append(value)
}
