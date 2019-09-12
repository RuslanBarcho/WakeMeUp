package io.vinter.wakemeup

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(io.vinter.wakemeup.di.modules)
        }
    }
}