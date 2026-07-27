package es.pictorario.app.domain

import kotlinx.serialization.Serializable

/** Highest sequence count the UI allows. Mirrors `Starter.MaxSecuencias`. */
const val MAX_SEQUENCES = 10

/**
 * Highest activity count per sequence. Mirrors `Starter.MaxActividades`, and is
 * bound to [Palette] having one colour per activity.
 */
const val MAX_ACTIVITIES = 20

/** Clock pictogram, used for a sequence that has no pictogram of its own. */
const val DEFAULT_PICTOGRAM_ID = 7229

/** Playing children, used when a new activity is added. */
const val NEW_ACTIVITY_PICTOGRAM_ID = 9813

/**
 * How the clock face is laid out. The B4A original stored this as a bare int in
 * `Tablero.tipo`, in this same order.
 */
@Serializable
enum class BoardType {
    /** 12-hour clock showing only the morning half of the day. */
    MORNING_12H,

    /** 12-hour clock showing only the afternoon half of the day. */
    AFTERNOON_12H,

    /** 24-hour clock: a full day around a single revolution. */
    DAY_24H,

    /** Open 300° arc spanning exactly the hours the sequence covers. */
    FULL_SEQUENCE,
}

/** How much of the current time the clock hands spell out. Was `Tablero.indicar_hora`. */
@Serializable
enum class TimeIndicator { NONE, HOUR, HOUR_MINUTE, HOUR_MINUTE_SECOND }

@Serializable
data class Activity(
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int,
    val pictogramId: Int,
    val description: String,
) {
    /** Minutes since midnight, the unit every schedule comparison works in. */
    val startMinutes: Int get() = startHour * 60 + startMinute
    val endMinutes: Int get() = endHour * 60 + endMinute

    /** Whether [minutesOfDay] falls in `[start, end)`. */
    fun contains(minutesOfDay: Int): Boolean =
        minutesOfDay >= startMinutes && minutesOfDay < endMinutes
}

@Serializable
data class Board(
    val type: BoardType = BoardType.FULL_SEQUENCE,
    val timeIndicator: TimeIndicator = TimeIndicator.HOUR,
    /** Side of the pictogram buttons as a percentage of screen width; 0 hides them. */
    val iconSizePercent: Int = 0,
)

@Serializable
data class Sequence(
    val description: String,
    val board: Board = Board(),
    val pictogramId: Int = DEFAULT_PICTOGRAM_ID,
    val activities: List<Activity> = emptyList(),
    val notifications: Boolean = false,
)

@Serializable
data class Settings(
    val alarmsEnabled: Boolean = true,
    val appProtected: Boolean = false,
    val format24h: Boolean = false,
    val hourColor: Long = 0xFF000000,
    val minuteColor: Long = 0xFF0000FF,
    val secondColor: Long = 0xFFFF0000,
    /** Last `versionCode` seen, so the changelog is shown once per update. */
    val installedVersion: Int = -1,
)

/** Everything the app persists, written as a single JSON document. */
@Serializable
data class AppData(
    val sequences: List<Sequence> = emptyList(),
    val settings: Settings = Settings(),
)
