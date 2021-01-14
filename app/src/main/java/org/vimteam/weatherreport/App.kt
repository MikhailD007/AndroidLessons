package org.vimteam.weatherreport

import android.app.Application
import android.content.res.Resources
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.vimteam.weatherreport.main.MainModule

class App: Application() {

    companion object {
        lateinit var appResources: Resources
    }

    override fun onCreate() {
        super.onCreate()
        appResources = resources
        startKoin {
            androidContext(this@App)
            modules(
                MainModule.get()
            )
        }
    }

}