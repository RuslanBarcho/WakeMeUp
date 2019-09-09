package io.vinter.wakemeup.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.vinter.wakemeup.entity.user.User
import io.vinter.wakemeup.entity.user.UserRepository
import io.vinter.wakemeup.network.form.LoginForm

class LoginViewModel : ViewModel() {

    var userData = MutableLiveData<User>()
    var error = MutableLiveData<String>()
    private val repository = UserRepository()

    fun getToken(form: LoginForm) {
        repository.authorize(form, userData::postValue)
        {error.postValue(it.localizedMessage)}
    }

}