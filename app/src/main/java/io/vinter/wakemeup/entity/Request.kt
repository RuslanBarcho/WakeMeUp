package io.vinter.wakemeup.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Request {

    @SerializedName("_id")
    @Expose
    var id: String? = null
    @SerializedName("from")
    @Expose
    var from: Friend? = null
    @SerializedName("to")
    @Expose
    var to: Friend? = null
    @SerializedName("accepted")
    @Expose
    var accepted: Boolean? = null

}