package io.vinter.wakemeup.ui.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.friends.Friend
import io.vinter.wakemeup.entity.friends.FriendsRepository

class FriendsViewModel(private val repository: FriendsRepository) : ViewModel() {

    val state = MutableLiveData<FriendsState>()
    val messages = MutableLiveData<Message>()
    val deletedFriend = MutableLiveData<Friend>()
    val error = MutableLiveData<String>()

    init {
        state.value = FriendsState.Initial()
    }

    fun getFiends() {
        if (state.value !is FriendsState.Success) state.postValue(FriendsState.Loading())
        repository.getFriends({friends ->
            state.postValue(FriendsState.Success(friends))
        }, {
            state.postValue(FriendsState.Error())
        })
    }

    fun deleteFriend(friend: Friend) = repository.deleteFriend(friend, deletedFriend::postValue) {
        error.postValue(it.localizedMessage)
    }

    fun sendCall(id: String) = repository.callFriend(id, messages::postValue) {
        error.postValue(it.localizedMessage)
    }

    fun sendRequest(query: String) = repository.sendFriendRequest(query, messages::postValue) {
        error.postValue(it.localizedMessage)
    }
}