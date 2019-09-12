package io.vinter.wakemeup.di

import io.vinter.wakemeup.di.modules.friendModule
import io.vinter.wakemeup.di.modules.networkModule
import io.vinter.wakemeup.di.modules.requestModule
import io.vinter.wakemeup.di.modules.userModule

val modules = mutableListOf(
        networkModule,
        friendModule,
        requestModule,
        userModule
)