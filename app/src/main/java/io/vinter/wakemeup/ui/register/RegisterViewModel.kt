package io.vinter.wakemeup.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.user.UserRepository
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.form.RegisterForm
import io.vinter.wakemeup.network.service.UserService

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    var message = MutableLiveData<Message>()
    var error = MutableLiveData<String>()

    fun register(form: RegisterForm) {
        repository.register(form, message::postValue) {error.postValue(it.localizedMessage)}
    }

}
