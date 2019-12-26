package io.vinter.wakemeup.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.vinter.wakemeup.data.preferences.PreferencesRepository
import io.vinter.wakemeup.ui.login.LoginActivity
import org.koin.android.ext.android.get

class LaunchActivity : AppCompatActivity() {

    private val preferences: PreferencesRepository = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preferences.getToken() == "") {
            this.startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
        } else {
            this.startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }
}
