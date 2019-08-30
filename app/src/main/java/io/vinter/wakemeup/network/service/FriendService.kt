package io.vinter.wakemeup.network.service

import io.reactivex.Single
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.Request
import io.vinter.wakemeup.network.form.CallForm
import io.vinter.wakemeup.network.form.SendRequestForm
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FriendService {

    @POST("/pair/sendCall")
    fun sendCall(@Header("Authorization")token: String, @Body form: CallForm): Single<Message>

    @POST("/request")
    fun sendRequest(@Header("Authorization")token: String, @Body form: SendRequestForm) : Single<Message>

    @GET("/request")
    fun getRequests(@Header("Authorization")token: String) : Single<ArrayList<Request>>

    @POST("/request/accept")
    fun acceptRequest(@Header("Authorization")token: String, @Body request: Request) : Single<Message>

    @POST("/request/reject")
    fun rejectRequest(@Header("Authorization")token: String, @Body request: Request): Single<Message>
}