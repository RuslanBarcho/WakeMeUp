package io.vinter.wakemeup.ui.register

import io.vinter.wakemeup.entity.Message

sealed class RegisterState {
    class NORMAL: RegisterState()
    class LOADING: RegisterState()
    class SUCCESS(val data: Message): RegisterState()
}