package io.vinter.wakemeup.di.modules

import io.vinter.wakemeup.entity.request.RequestRepository
import io.vinter.wakemeup.network.service.FriendService
import io.vinter.wakemeup.ui.request.RequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val requestModule = module {
    viewModel { RequestViewModel(get()) }
    single { RequestRepository(get()) }
}