package io.vinter.wakemeup.ui.login

import io.vinter.wakemeup.entity.user.User

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    class Success(val userData: User): LoginState()
}
