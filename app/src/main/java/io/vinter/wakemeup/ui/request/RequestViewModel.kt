package io.vinter.wakemeup.ui.request

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.Request
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.service.FriendService

@SuppressLint("CheckResult")
class RequestViewModel : ViewModel(){

    var state = MutableLiveData<RequestState>()
    var message = MutableLiveData<Pair<Message, Request>>()

    init {
        state.value = RequestState.Initial()
    }

    fun getRequests(token: String){
        if (state.value !is RequestState.Success) state.postValue(RequestState.Loading())
        NetModule.retrofit.create(FriendService::class.java)
                .getRequests("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({requests ->
                    if (requests.size > 0)state.postValue(RequestState.Success(requests))
                    else state.postValue(RequestState.Empty())
                }, {
                    state.postValue(RequestState.Error())
                })
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