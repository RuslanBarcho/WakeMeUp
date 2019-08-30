package io.vinter.wakemeup.ui.request

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.Request
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.service.FriendService

class RequestViewModel : ViewModel(){
    var requests = MutableLiveData<ArrayList<Request>>()
    var message = MutableLiveData<Pair<Message, Request>>()
    var loading = false

    fun getRequests(token: String){
        loading = true
        NetModule.retrofit.create(FriendService::class.java)
                .getRequests("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess{loading = false}
                .subscribe(requests::postValue) { e ->
                    Log.e("Network", e.message)
                    loading = false
                }
    }

    fun acceptRequest(token: String, request: Request){
        NetModule.retrofit.create(FriendService::class.java)
                .acceptRequest("Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({message.postValue(Pair(it, request))}, {e ->
                    Log.e("Network", e.message)
                })
    }

    fun rejectRequest(token: String, request: Request){
        NetModule.retrofit.create(FriendService::class.java)
                .rejectRequest("Bearer $token", request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({message.postValue(Pair(it, request))}, {e ->
                    Log.e("Network", e.message)
                })
    }
}