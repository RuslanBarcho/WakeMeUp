package io.vinter.wakemeup.ui.login

import io.vinter.wakemeup.entity.user.User

sealed class LoginState {
    class NORMAL: LoginState()
    class LOADING: LoginState()
    class SUCCESS(val userData: User): LoginState()
}