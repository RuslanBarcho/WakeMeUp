package io.vinter.wakemeup.ui.friends

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.friends.FriendsRepository

class FriendsViewModel : ViewModel() {

    var state = MutableLiveData<FriendsState>()
    var messages = MutableLiveData<Message>()
    var error = MutableLiveData<String>()
    private val repository = FriendsRepository()

    init {
        state.value = FriendsState.Initial()
    }

    fun getFiends(token: String) {
        if (state.value !is FriendsState.Success) state.postValue(FriendsState.Loading())
        repository.getFriends(token, {friends ->
            state.postValue(FriendsState.Success(friends))
        }, {
            state.postValue(FriendsState.Error())
        })
    }

    fun sendCall(token: String, id: String) {
        repository.callFriend(token, id, messages::postValue) {error.postValue(it.localizedMessage)}
    }

    fun sendRequest(token: String, query: String){
        repository.sendFriendRequest(token, query, messages::postValue) {error.postValue(it.localizedMessage)}
    }
}