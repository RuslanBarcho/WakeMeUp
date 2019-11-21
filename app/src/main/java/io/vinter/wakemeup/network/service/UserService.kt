package io.vinter.wakemeup.network.service

import io.reactivex.Single
import io.vinter.wakemeup.entity.user.User
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.form.LoginForm
import io.vinter.wakemeup.network.form.RegisterForm
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/user/login")
    fun getToken(@Body form: LoginForm): Single<User>

    @POST("/user/signup")
    fun register(@Body form: RegisterForm): Single<Message>
}