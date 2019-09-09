package io.vinter.wakemeup.ui.login

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.LoginResponse
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.form.LoginForm
import io.vinter.wakemeup.network.service.UserService
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    var userData = MutableLiveData<LoginResponse>()
    var error = MutableLiveData<String>()
    var state = MutableLiveData<Int>()

    @SuppressLint("CheckResult")
    fun getToken(form: LoginForm) {
        state.postValue(1)
        NetModule.retrofit.create(UserService::class.java)
                .getToken(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.postValue(0) }
                .subscribe({ loginResponse ->
                    userData.postValue(loginResponse)
                }, { e ->
                    if (e is HttpException) error.postValue(e.localizedMessage) else error.postValue("Problem with internet connection")
                })
    }

}