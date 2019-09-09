package io.vinter.wakemeup.entity.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("login")
    @Expose
    var login: String? = null
    @SerializedName("pictureURL")
    @Expose
    var pictureURL: String? = null
    @SerializedName("id")
    @Expose
    var id: String? = null

}