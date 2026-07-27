package es.pictorario.app.domain

/**
 * The three sequences a fresh install starts with, copied verbatim from
 * `Starter.Inicializar_Con_Ejemplo`.
 */
object SampleData {

    fun initialData(): AppData = AppData(sequences = sequences(), settings = Settings())

    fun sequences(): List<Sequence> = listOf(dayWithClassesFromHome, freeAfternoon, beforeSchool)

    private val dayWithClassesFromHome = Sequence(
        description = "Día con clases desde casa",
        board = Board(BoardType.FULL_SEQUENCE, TimeIndicator.HOUR, iconSizePercent = 0),
        pictogramId = 26799,
        activities = listOf(
            Activity(9, 0, 10, 0, 31857, "Despertarse con energía"),
            Activity(10, 0, 13, 0, 32556, "Tarea del cole"),
            Activity(13, 0, 14, 0, 32568, "Tiempo libre"),
            Activity(14, 0, 15, 0, 4611, "Comemos"),
            Activity(15, 0, 17, 0, 2587, "Peli con palomitas"),
            Activity(17, 0, 17, 30, 13040, "Deporte en casa"),
            Activity(17, 30, 19, 0, 32580, "Juegos en familia"),
            Activity(19, 0, 20, 0, 11653, "Tiempo libre"),
            Activity(20, 0, 20, 30, 2271, "Baño y cena"),
            Activity(20, 30, 21, 0, 2369, "Leemos y a dormir"),
        ),
    )

    private val freeAfternoon = Sequence(
        description = "Tarde libre",
        board = Board(
            BoardType.AFTERNOON_12H,
            TimeIndicator.HOUR_MINUTE_SECOND,
            iconSizePercent = 0,
        ),
        pictogramId = 9813,
        activities = listOf(
            Activity(15, 0, 17, 0, 9813, "Jugar"),
            Activity(17, 0, 18, 0, 32556, "Hacer los deberes"),
            Activity(18, 0, 20, 30, 9813, "Jugar"),
            Activity(20, 30, 21, 0, 2271, "Bañarse"),
            Activity(21, 0, 22, 0, 28675, "Cenar"),
            Activity(22, 0, 22, 30, 2369, "Acostarse"),
        ),
    )

    private val beforeSchool = Sequence(
        description = "Antes de ir al cole",
        board = Board(BoardType.FULL_SEQUENCE, TimeIndicator.NONE, iconSizePercent = 15),
        pictogramId = 3082,
        activities = listOf(
            Activity(8, 0, 8, 15, 2781, "Vestirse"),
            Activity(8, 15, 8, 30, 28667, "Desayunar"),
            Activity(8, 30, 8, 35, 9813, "Coger un juguete"),
            Activity(8, 35, 9, 0, 3082, "Ir andando al cole"),
        ),
    )

    /** Pictograms shipped in `assets/pictogramas`, seeded on first launch. */
    val BUNDLED_PICTOGRAM_IDS = listOf(
        31857, 2781, 28667, 3082, 28206, 9813, 2271, 28675, 2369,
        7229, 26799, 32556, 32568, 4611, 2587, 13040, 32580, 11653,
    )
}
