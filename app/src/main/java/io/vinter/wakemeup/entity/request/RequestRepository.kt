package io.vinter.wakemeup.entity.request

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.service.FriendService

class RequestRepository {
    fun getRequests(token: String, onSuccess: (friends: ArrayList<Request>) -> Unit, onError: (e: Throwable) -> Unit): Disposable{
        return NetModule.retrofit.create(FriendService::class.java)
                .getRequests("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun acceptRequest(token: String, request: Request, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return NetModule.retrofit.create(FriendService::class.java)
                .acceptRequest("Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun rejectRequest(token: String, request: Request, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return NetModule.retrofit.create(FriendService::class.java)
                .rejectRequest("Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }
}