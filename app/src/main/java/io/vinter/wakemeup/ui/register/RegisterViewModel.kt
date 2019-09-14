package io.vinter.wakemeup.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.vinter.wakemeup.entity.user.UserRepository
import io.vinter.wakemeup.network.form.RegisterForm

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    var state = MutableLiveData<RegisterState>()
    var error = MutableLiveData<String>()

    fun register(form: RegisterForm) {
        state.postValue(RegisterState.LOADING())
        repository.register(form, {RegisterState.SUCCESS(it)}) {
            state.postValue(RegisterState.NORMAL())
            error.postValue(it.localizedMessage)}
    }

}
