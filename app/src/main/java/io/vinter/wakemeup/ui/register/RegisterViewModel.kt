package io.vinter.wakemeup.ui.register

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.form.RegisterForm
import io.vinter.wakemeup.network.service.UserService
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {

    var message = MutableLiveData<Message>()
    var error = MutableLiveData<String>()

    @SuppressLint("CheckResult")
    fun register(form: RegisterForm) {
        NetModule.retrofit.create(UserService::class.java)
                .register(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(message::postValue) { e ->
                    if (e is HttpException) error.postValue(e.message()) else error.postValue("Problem with internet connection") }
    }

}
