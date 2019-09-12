package io.vinter.wakemeup.di.modules

import io.vinter.wakemeup.entity.friends.FriendsRepository
import io.vinter.wakemeup.network.service.FriendService
import io.vinter.wakemeup.network.service.UserService
import io.vinter.wakemeup.ui.friends.FriendsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val friendModule = module {
    viewModel { FriendsViewModel(get()) }
    single { FriendsRepository(get(), get()) }
    single { get<Retrofit>().create(FriendService::class.java) }
    single { get<Retrofit>().create(UserService::class.java) }
}