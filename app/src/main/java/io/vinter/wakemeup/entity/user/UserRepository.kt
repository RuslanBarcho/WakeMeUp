package io.vinter.wakemeup.entity.user

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.network.NetModule
import io.vinter.wakemeup.network.form.LoginForm
import io.vinter.wakemeup.network.service.UserService

class UserRepository{
    fun authorize(form: LoginForm, onSuccess: (user: User) -> Unit, onError: (e: Throwable) -> Unit): Disposable{
        return NetModule.retrofit.create(UserService::class.java)
                .getToken(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }
}