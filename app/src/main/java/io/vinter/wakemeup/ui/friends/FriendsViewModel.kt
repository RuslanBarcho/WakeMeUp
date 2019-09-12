package io.vinter.wakemeup.ui.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.friends.FriendsRepository

class FriendsViewModel(private val repository: FriendsRepository) : ViewModel() {

    var state = MutableLiveData<FriendsState>()
    var messages = MutableLiveData<Message>()
    var error = MutableLiveData<String>()

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