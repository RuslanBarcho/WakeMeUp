package io.vinter.wakemeup.di

import io.vinter.wakemeup.di.modules.friendModule
import io.vinter.wakemeup.di.modules.networkModule

val modules = mutableListOf(
        friendModule,
        networkModule
)