package io.vinter.wakemeup

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext
import io.vinter.wakemeup.di.modules

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }
}