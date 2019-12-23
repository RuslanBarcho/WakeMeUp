package io.vinter.wakemeup.entity.request

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.service.FriendService

class RequestRepository(private val service: FriendService) {
    fun getRequests(onSuccess: (friends: ArrayList<Request>) -> Unit, onError: (e: Throwable) -> Unit): Disposable{
        return service
                .getRequests()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun acceptRequest(request: Request, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return service
                .acceptRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun rejectRequest(request: Request, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return service
                .rejectRequest(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }
}