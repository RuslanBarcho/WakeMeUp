package io.vinter.wakemeup.entity.user

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.form.LoginForm
import io.vinter.wakemeup.network.form.RegisterForm
import io.vinter.wakemeup.network.service.UserService

class UserRepository(private val service: UserService) {

    fun authorize(form: LoginForm, onSuccess: (user: User) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return service
                .getToken(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    fun register(form: RegisterForm, onSuccess: (message: Message) -> Unit, onError: (e: Throwable) -> Unit): Disposable {
        return service
                .register(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }
}