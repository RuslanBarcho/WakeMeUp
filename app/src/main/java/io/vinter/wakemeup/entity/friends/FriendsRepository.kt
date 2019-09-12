package io.vinter.wakemeup.entity.friends

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.form.CallForm
import io.vinter.wakemeup.network.form.FriendRequestForm
import io.vinter.wakemeup.network.service.FriendService
import io.vinter.wakemeup.network.service.UserService

class FriendsRepository(private val friendsService: FriendService, private val userService: UserService) {
    fun getFriends(token: String, onSuccess: (friends: ArrayList<Friend>) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return userService
                .getFriends("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun callFriend(token: String, id: String, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return friendsService
                .sendCall("Bearer $token", CallForm(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun sendFriendRequest(token: String, query: String, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return friendsService
                .sendRequest("Bearer $token", FriendRequestForm(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

}