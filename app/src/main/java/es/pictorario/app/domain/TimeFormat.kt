package es.pictorario.app.domain

/**
 * Renders clock times the way the B4A original did (`Visualizacion.EscribirHora`
 * and `Hora24a12`), with one correction.
 *
 * The original mapped both midnight and noon to `00:00` in 12-hour mode, because
 * it subtracted 12 only when the hour was already past 11 and never mapped a
 * zero back up to twelve. Here both render as `12:00`, which is what
 * `Hora24a12` itself did and what a 12-hour clock is expected to show.
 */
object TimeFormat {

    /** `"13:05"` in 24-hour mode, `"01:05 p.m."` in 12-hour mode. */
    fun time(hour: Int, minute: Int, format24h: Boolean): String {
        val text = "%02d:%02d".format(displayHour(hour, format24h), minute)
        if (format24h) return text
        return if (hour < 12) "$text a.m." else "$text p.m."
    }

    /** The number to print on the dial for a given 24-hour value. */
    fun displayHour(hour24: Int, format24h: Boolean): Int {
        if (format24h) return hour24
        val hour = hour24.mod(24)
        return if (hour % 12 == 0) 12 else hour % 12
    }

    /**
     * The digital readout above the dial. Unlike [time] it names midnight and
     * noon rather than using a.m./p.m. — see `Visualizacion.Temporizador_Tick`.
     */
    fun digitalClock(hour: Int, minute: Int, format24h: Boolean): String {
        val text = "%02d:%02d".format(displayHour(hour, format24h), minute)
        if (format24h) return text
        val suffix = when {
            hour == 0 -> " de la noche"
            hour == 12 -> " del mediodía"
            hour > 12 -> " p.m."
            else -> " a.m."
        }
        return text + suffix
    }
}
