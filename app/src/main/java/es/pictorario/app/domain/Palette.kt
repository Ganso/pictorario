package es.pictorario.app.domain

/**
 * Colours for the activity sectors, taken from ColorBrewer: the eleven usable
 * entries of Set3 followed by Paired.
 *
 * The B4A original declared `Colores(20)` but filled only 19 values
 * (`Starter.bas:66`), leaving the twentieth activity painted transparent. The
 * missing entry is restored here as Paired's next colour, `#cab2d6`.
 */
object Palette {

    private val colors = longArrayOf(
        0xFF8DD3C7, 0xFFFFFFB3, 0xFFBEBADA, 0xFFFB8072, 0xFF80B1D3,
        0xFFFDB462, 0xFFB3DE69, 0xFFFCCDE5, 0xFFD9D9D9, 0xFFBC80BD,
        0xFFCCEBC5, 0xFFA6CEE3, 0xFF1F78B4, 0xFFB2DF8A, 0xFF33A02C,
        0xFFFB9A99, 0xFFE31A1C, 0xFFFDBF6F, 0xFFFF7F00, 0xFFCAB2D6,
    )

    val size: Int get() = colors.size

    /** Colour for the activity at [index], wrapping if the list ever grows. */
    fun color(index: Int): Long = colors[index.mod(colors.size)]
}

/** Labels for the board-type picker, in [BoardType] order. */
val BOARD_TYPE_LABELS = listOf(
    "Reloj de 12h (mañana)",
    "Reloj de 12h (tarde)",
    "Reloj de 24h",
    "Secuencia completa",
)

/** Labels for the time-indicator picker, in [TimeIndicator] order. */
val TIME_INDICATOR_LABELS = listOf(
    "Sin indicación",
    "Indicar hora",
    "Indicar hora y minutos",
    "Indicar hora, minutos y segundos",
)
