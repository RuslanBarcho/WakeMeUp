package io.vinter.wakemeup.data.preferences

import android.content.Context
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.volume.VolumeStates
import io.vinter.wakemeup.entity.LoginResponse

class PreferencesRepository(context: Context) {

    private var preferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

    fun setVibrationNeed(value: Boolean) { preferences.edit().putBoolean("vibrationNeed", value).apply() }

    fun getVibrationNeed(): Boolean {return preferences.getBoolean("vibrationNeed", false) }

    fun setUserInfo(user: LoginResponse) {
        preferences.edit().putString("token", user.token)
                .putString("login", user.login)
                .putString("pictureURL", user.pictureURL)
                .putString("id", user.id)
                .apply()
    }

    fun getToken(): String { return preferences.getString("token", "")!! }

    fun getUserId(): String? { return preferences.getString("id", "") }

    fun getPictureUrl(): String? { return preferences.getString("pictureURL", "") }

    fun setVolume(value: VolumeStates){
        preferences.edit().putString("vol", value.toString()).apply()
    }

    fun getVolume(): VolumeStates{
        return VolumeStates.valueOf(preferences.getString("vol", "MEDIUM")!!)
    }
}