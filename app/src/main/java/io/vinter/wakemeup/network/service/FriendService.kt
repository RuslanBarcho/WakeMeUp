package io.vinter.wakemeup.network.service

import io.reactivex.Single
import io.vinter.wakemeup.entity.Message
import io.vinter.wakemeup.entity.friends.Friend
import io.vinter.wakemeup.entity.request.Request
import io.vinter.wakemeup.network.form.CallForm
import io.vinter.wakemeup.network.form.FriendRequestForm
import retrofit2.http.*

interface FriendService {
    @GET("/user/friends")
    fun getFriends(): Single<ArrayList<Friend>>

    @DELETE("/user/friends/")
    fun deleteFriend(@Query("friendId") friendId: String?): Single<Message>

    @POST("/call/send")
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