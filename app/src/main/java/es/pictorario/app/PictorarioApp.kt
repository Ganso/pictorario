package es.pictorario.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import es.pictorario.app.alarm.Notifications
import es.pictorario.app.data.PictogramRepository
import es.pictorario.app.data.createAppDataStore
import es.pictorario.app.domain.AppData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Process-wide holder for the few things that must exist exactly once.
 *
 * DataStore refuses to have two instances open on the same file, and the alarm
 * receiver reads the same document the UI does. Creating one per component threw
 * `IllegalStateException` the moment an alarm fired while the app was open, so
 * both go through here. Its scope lives as long as the process, which also means
 * the store survives the activity being recreated.
 */
class PictorarioApp : Application() {

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val dataStore: DataStore<AppData> by lazy { createAppDataStore(this, appScope) }
    val pictograms: PictogramRepository by lazy { PictogramRepository(this) }

    override fun onCreate() {
        super.onCreate()
        Notifications.createChannels(this)
    }
}

/** The single container, reachable from an activity or a receiver alike. */
val Context.pictorario: PictorarioApp
    get() = applicationContext as PictorarioApp
