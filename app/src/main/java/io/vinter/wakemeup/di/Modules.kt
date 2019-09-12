package io.vinter.wakemeup.di

import io.vinter.wakemeup.di.modules.*

val modules = mutableListOf(
        networkModule,
        settingsModule,
        friendModule,
        requestModule,
        userModule
)