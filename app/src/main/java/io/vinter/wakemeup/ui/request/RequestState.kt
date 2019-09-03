package io.vinter.wakemeup.ui.request

import io.vinter.wakemeup.entity.Request

sealed class RequestState {
    class Initial: RequestState()
    class Loading: RequestState()
    class Success(val data: ArrayList<Request>): RequestState()
    class Error: RequestState()
}