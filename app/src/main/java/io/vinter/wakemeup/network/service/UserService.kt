package io.vinter.wakemeup.network.service

import io.reactivex.Single
import io.vinter.wakemeup.entity.Friend
import io.vinter.wakemeup.entity.LoginResponse
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.network.form.LoginForm
import io.vinter.wakemeup.network.form.RegisterForm
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("/user/login")
    fun getToken(@Body form: LoginForm): Single<LoginResponse>

    @POST("/user/signup")
    fun register(@Body form: RegisterForm): Single<Message>

    @GET("/user/friends")
    fun getFriends(@Header("Authorization") token: String): Single<ArrayList<Friend>>
}