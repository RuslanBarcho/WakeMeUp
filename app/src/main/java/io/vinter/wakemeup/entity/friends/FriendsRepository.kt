package io.vinter.wakemeup.entity.friends

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.form.CallForm
import io.vinter.wakemeup.network.form.FriendRequestForm
import io.vinter.wakemeup.network.service.FriendService
import io.vinter.wakemeup.network.service.UserService

class FriendsRepository {
    fun getFriends(token: String, onSuccess: (friends: ArrayList<Friend>) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return NetModule.retrofit.create(UserService::class.java)
                .getFriends("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun callFriend(token: String, id: String, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return NetModule.retrofit.create(FriendService::class.java)
                .sendCall("Bearer $token", CallForm(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun sendFriendRequest(token: String, query: String, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return NetModule.retrofit.create(FriendService::class.java)
                .sendRequest("Bearer $token", FriendRequestForm(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

}