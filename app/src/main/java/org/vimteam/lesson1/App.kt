package org.vimteam.lesson1

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.vimteam.lesson1.main.MainModule

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