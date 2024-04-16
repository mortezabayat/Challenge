package co.meteto.cat.core.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CatApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.initPreferences(this)
    }
}