package io.vinter.wakemeup.network.service

import io.reactivex.Single
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.friends.Friend
import io.vinter.wakemeup.entity.request.Request
import io.vinter.wakemeup.network.form.CallForm
import io.vinter.wakemeup.network.form.FriendRequestForm
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FriendService {
    @GET("/user/friends")
    fun getFriends(): Single<ArrayList<Friend>>

    @POST("/pair/sendCall")
    fun sendCall(@Body form: CallForm): Single<Message>

    @POST("/request")
    fun sendRequest(@Body form: FriendRequestForm) : Single<Message>

    @GET("/request")
    fun getRequests() : Single<ArrayList<Request>>

    @POST("/request/accept")
    fun acceptRequest(@Body request: Request) : Single<Message>

    @POST("/request/reject")
    fun rejectRequest(@Body request: Request): Single<Message>
}