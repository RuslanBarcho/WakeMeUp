package io.vinter.wakemeup.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.vinter.wakemeup.entity.user.User
import io.vinter.wakemeup.entity.user.UserRepository
import io.vinter.wakemeup.network.form.LoginForm

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var userData = MutableLiveData<User>()
    var error = MutableLiveData<String>()

    fun getToken(form: LoginForm) {
        repository.authorize(form, userData::postValue)
        {error.postValue(it.localizedMessage)}
    }

}