package io.vinter.wakemeup.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.preferences.PreferencesRepository
import io.vinter.wakemeup.ui.settings.volume.VolumeSetFragment
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.get

class SettingsActivity : AppCompatActivity() {

    private val preferences: PreferencesRepository = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setting_volume.setOnClickListener {
            val volumeSetFragment = VolumeSetFragment()
            volumeSetFragment.show(supportFragmentManager, "volume_set")
        }
        settings_back.setOnClickListener {
            finish()
        }

        settings_vibration_switch.isChecked = preferences.getVibrationNeed()

        settings_vibration_switch.setOnCheckedChangeListener { _, b -> preferences.setVibrationNeed(b)}

        setting_vibration.setOnClickListener { settings_vibration_switch.isChecked = !settings_vibration_switch.isChecked }

        settings_logout.setOnClickListener {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(preferences.getUserTopic()).addOnCompleteListener {
                if (it.isSuccessful){
                    preferences.clearUserTopic()
                    preferences.clearToken()
                    setResult(45)
                    finish()
                }
            }
        }
    }
}
