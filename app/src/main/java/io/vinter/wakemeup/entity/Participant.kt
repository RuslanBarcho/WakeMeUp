package io.vinter.wakemeup.entity


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Participant {

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