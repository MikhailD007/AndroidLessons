package org.vimteam.weatherreport

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.vimteam.weatherreport.main.MainModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                MainModule.get()
            )
        }
    }

}