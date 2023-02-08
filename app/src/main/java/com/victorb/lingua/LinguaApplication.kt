package com.victorb.lingua

import android.app.Application
import com.victorb.lingua.infrastructure.database.room.DatabasePreloader
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class LinguaApplication : Application() {

    @Inject
    lateinit var databasePreloader: Provider<DatabasePreloader>

    override fun onCreate() {
        super.onCreate()

        databasePreloader.get().preload()
    }

}