package io.vinter.wakemeup.di.modules

import io.vinter.wakemeup.entity.user.UserRepository
import io.vinter.wakemeup.network.service.FriendService
import io.vinter.wakemeup.network.service.UserService
import io.vinter.wakemeup.ui.login.LoginViewModel
import io.vinter.wakemeup.ui.register.RegisterViewModel
import io.vinter.wakemeup.ui.request.RequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val userModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    single { UserRepository(get()) }
    single { get<Retrofit>().create(UserService::class.java) }
}