package io.vinter.wakemeup.entity.friends

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.service.UserService

class FriendsRepository {
    fun getFriends(token: String, onSuccess: (friends: ArrayList<Friend>) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return NetModule.retrofit.create(UserService::class.java)
                .getFriends("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }
}