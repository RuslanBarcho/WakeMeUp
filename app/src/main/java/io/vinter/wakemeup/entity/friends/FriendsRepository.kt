package io.vinter.wakemeup.entity.friends

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.form.CallForm
import io.vinter.wakemeup.network.form.FriendRequestForm
import io.vinter.wakemeup.network.service.FriendService

class FriendsRepository(private val friendsService: FriendService) {
    fun getFriends(onSuccess: (friends: ArrayList<Friend>) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return friendsService
                .getFriends()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun callFriend(id: String, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return friendsService
                .sendCall(CallForm(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun sendFriendRequest(query: String, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return friendsService
                .sendRequest(FriendRequestForm(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

}