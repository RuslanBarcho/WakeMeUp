package io.vinter.wakemeup.di.modules

import io.vinter.wakemeup.data.preferences.PreferencesRepository
import org.koin.dsl.module

val settingsModule = module {
    single { PreferencesRepository(get()) }
}