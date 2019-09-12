package io.vinter.wakemeup.ui.settings.volume

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.preferences.PreferencesRepository
import io.vinter.wakemeup.data.volume.VolumeStates
import io.vinter.wakemeup.ui.view.BaseDialog
import kotlinx.android.synthetic.main.fragment_volume_set.*
import org.koin.android.ext.android.get

class VolumeSetFragment : DialogFragment() {

    private val preferences: PreferencesRepository = get()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_volume_set, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (preferences.getVolume()){
            VolumeStates.OFF -> volume_set_group.check(R.id.volume_set_low)
            VolumeStates.MEDIUM -> volume_set_group.check(R.id.volume_set_medium)
            VolumeStates.MAX -> volume_set_group.check(R.id.volume_set_max)
        }
        volume_set_group.setOnCheckedChangeListener { _, i ->
            when (i){
                R.id.volume_set_low -> preferences.setVolume(VolumeStates.OFF)
                R.id.volume_set_medium -> preferences.setVolume(VolumeStates.MEDIUM)
                R.id.volume_set_max -> preferences.setVolume(VolumeStates.MAX)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return BaseDialog.get(dialog, R.layout.fragment_volume_set)
    }

}
