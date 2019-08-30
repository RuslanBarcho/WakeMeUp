package io.vinter.wakemeup.ui.friends

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.R
import io.vinter.wakemeup.entity.Friend
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.form.CallForm
import io.vinter.wakemeup.network.form.SendRequestForm
import io.vinter.wakemeup.network.service.FriendService
import io.vinter.wakemeup.network.service.UserService

@SuppressLint("CheckResult")
class FriendsViewModel : ViewModel() {

    var friends = MutableLiveData<ArrayList<Friend>>()
    var messages = MutableLiveData<Message>()
    var error = MutableLiveData<String>()
    var loading = false

    fun getFiends(token: String, context: Context) {
        loading = true
        NetModule.retrofit.create(UserService::class.java)
                .getFriends("Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess{loading = false}
                .subscribe(this.friends::postValue) { e ->
                    loading = false
                    error.postValue(context.getString(R.string.unable_load_friends))
                }
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