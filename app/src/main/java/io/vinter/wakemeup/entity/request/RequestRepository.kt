package io.vinter.wakemeup.entity.request

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.service.FriendService

class RequestRepository(private val service: FriendService) {
    fun getRequests(token: String, onSuccess: (friends: ArrayList<Request>) -> Unit, onError: (e: Throwable) -> Unit): Disposable{
        return service
                .getRequests("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun acceptRequest(token: String, request: Request, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return service
                .acceptRequest("Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun rejectRequest(token: String, request: Request, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return service
                .rejectRequest("Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }
}