package io.vinter.wakemeup.entity

import android.content.Context
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.vinter.wakemeup.R

class Message {

    @SerializedName("message")
    @Expose
    var message: String? = null

    fun getLocalizedMessage(context: Context?): String? {
        return when(message){
            "Call sent" -> context?.getString(R.string.message_call_sent)
            else -> message
        }
    }

}