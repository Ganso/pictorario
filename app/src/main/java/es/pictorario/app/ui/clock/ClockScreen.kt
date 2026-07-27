package es.pictorario.app.ui.clock

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.pictorario.app.R
import es.pictorario.app.domain.BoardType
import es.pictorario.app.ui.PictorarioState

/**
 * The visualisation screen. For now it shows the static board only; the hands,
 * the pictogram buttons and the activity carousel come next.
 */
@Composable
fun ClockScreen(state: PictorarioState, sequenceIndex: Int) {
    val sequence = state.sequences.getOrNull(sequenceIndex) ?: return
    val settings = state.settings
    val screenHeightPx = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }

    BackHandler(enabled = !settings.appProtected) { state.navigateHome() }

    Column(
        modifier = Modifier.fillMaxSize().background(BoardBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // The controls sit inside the board panel, 30 dp above its bottom edge,
        // filling the space the dial leaves empty — see the designer script
        // LS_visualizarsecuencia.java.
        Box(Modifier.fillMaxWidth().aspectRatio(BOARD_ASPECT_RATIO)) {
            ClockBoard(
                sequence = sequence,
                format24h = settings.format24h,
                screenHeightPx = screenHeightPx,
                modifier = Modifier.fillMaxSize(),
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                if (!settings.appProtected) {
                    Button(
                        onClick = state::navigateHome,
                        modifier = Modifier.weight(1f).height(60.dp),
                    ) {
                        Text("Cerrar visualización", fontSize = 18.sp)
                    }
                } else {
                    Box(Modifier.weight(1f))
                }

                Image(
                    painter = painterResource(
                        boardIcon(sequence.board.type, settings.appProtected),
                    ),
                    contentDescription = "Cambiar vista",
                    modifier = Modifier.size(60.dp).clickable {
                        state.cycleBoardType(sequenceIndex)
                    },
                )
            }
        }
    }
}

/** The `CambiarVista` button mirrors the board it will switch to. */
private fun boardIcon(boardType: BoardType, appProtected: Boolean): Int = when {
    appProtected -> R.drawable.candado
    boardType == BoardType.MORNING_12H -> R.drawable.manana
    boardType == BoardType.AFTERNOON_12H -> R.drawable.tarde
    boardType == BoardType.DAY_24H -> R.drawable.dia
    else -> R.drawable.fila
}
