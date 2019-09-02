package io.vinter.wakemeup.data

import android.content.Context
import io.vinter.wakemeup.R
import io.vinter.wakemeup.entity.LoginResponse

class PreferencesRepository(context: Context) {

    private var preferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

    fun setVolume(value: Int) { preferences.edit().putInt("volume", value).apply() }

    fun getVolume(): Int { return preferences.getInt("volume", R.id.volume_set_medium) }

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
}