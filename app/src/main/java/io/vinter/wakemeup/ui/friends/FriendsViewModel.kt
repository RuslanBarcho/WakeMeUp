package io.vinter.wakemeup.ui.friends

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.form.CallForm
import io.vinter.wakemeup.network.form.SendRequestForm
import io.vinter.wakemeup.network.service.FriendService
import io.vinter.wakemeup.network.service.UserService

@SuppressLint("CheckResult")
class FriendsViewModel : ViewModel() {

    var state = MutableLiveData<FriendsState>()
    var messages = MutableLiveData<Message>()
    var error = MutableLiveData<String>()

    fun getFiends(token: String) {
        state.postValue(FriendsState.Loading())
        NetModule.retrofit.create(UserService::class.java)
                .getFriends("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({friends ->
                    state.postValue(FriendsState.Success(friends))
                }, {
                    state.postValue(FriendsState.Error())
                })
    }

    fun sendCall(token: String, id: String) {
        NetModule.retrofit.create(FriendService::class.java)
                .sendCall("Bearer $token", CallForm(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messages::postValue) { e ->
                    Log.e("Network", e.message)
                }
    }

    fun sendRequest(token: String, query: String){
        NetModule.retrofit.create(FriendService::class.java)
                .sendRequest("Bearer $token", SendRequestForm(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messages::postValue) { e ->
                    Log.e("Network", e.message)
                }
    }
}