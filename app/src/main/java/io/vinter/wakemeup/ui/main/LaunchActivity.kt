package io.vinter.wakemeup.ui.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.preferences.PreferencesRepository
import io.vinter.wakemeup.ui.login.LoginActivity

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        val preferences = PreferencesRepository(this)
        if (preferences.getToken() == "") {
            this.startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
        } else {
            this.startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }
}
