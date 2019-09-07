package io.vinter.wakemeup.entity.friends


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Friend {

    @SerializedName("_id")
    @Expose
    var id: String? = null
    @SerializedName("login")
    @Expose
    var login: String? = null
    @SerializedName("pictureURL")
    @Expose
    var pictureURL: String? = null

}