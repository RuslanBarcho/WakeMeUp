package io.vinter.wakemeup.ui.request

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.request.Request
import io.vinter.wakemeup.entity.request.RequestRepository

class RequestViewModel(private val repository: RequestRepository) : ViewModel(){

    var state = MutableLiveData<RequestState>()
    var message = MutableLiveData<Pair<Message, Request>>()

    init {
        state.value = RequestState.Initial()
    }

    fun getRequests() {
        if (state.value !is RequestState.Success) state.postValue(RequestState.Loading())
        repository.getRequests( { requests ->
            if (requests.size > 0)state.postValue(RequestState.Success(requests))
            else state.postValue(RequestState.Empty())
        }, {
            state.postValue(RequestState.Error())
        })
    }

    fun acceptRequest(request: Request) {
        repository.acceptRequest(request, {
            message.postValue(Pair(it, request))
        }, { Log.e("Network", it.message) } )
    }

    fun rejectRequest(request: Request) {
        repository.rejectRequest(request, {
            message.postValue(Pair(it, request))
        }, { Log.e("Network", it.message) } )
    }
}