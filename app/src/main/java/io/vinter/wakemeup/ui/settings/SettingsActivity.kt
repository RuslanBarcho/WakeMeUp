package io.vinter.wakemeup.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.preferences.PreferencesRepository
import io.vinter.wakemeup.ui.settings.volume.VolumeSetFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val preferences = PreferencesRepository(this)

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
    }
}
