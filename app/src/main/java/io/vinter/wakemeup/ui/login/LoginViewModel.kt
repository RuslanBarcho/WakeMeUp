package io.vinter.wakemeup.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.vinter.wakemeup.entity.user.UserRepository
import io.vinter.wakemeup.network.form.LoginForm

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var state = MutableLiveData<LoginState>()
    var error = MutableLiveData<String>()

    fun getToken(form: LoginForm) {
        state.postValue(LoginState.LOADING())
        repository.authorize(form, {user ->
            state.postValue(LoginState.SUCCESS(user))
        })
        {
            state.postValue(LoginState.NORMAL())
            error.postValue(it.localizedMessage)
        }
    }

}