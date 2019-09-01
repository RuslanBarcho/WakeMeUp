package io.vinter.wakemeup.ui.settings.volume

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.PreferencesRepository
import kotlinx.android.synthetic.main.fragment_volume_set.*

class VolumeSetFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_volume_set, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferencesRepository = PreferencesRepository(context!!)
        when (preferencesRepository.getVolume()){
            R.id.volume_set_low -> volume_set_group.check(R.id.volume_set_low)
            R.id.volume_set_medium -> volume_set_group.check(R.id.volume_set_medium)
            R.id.volume_set_max -> volume_set_group.check(R.id.volume_set_max)
        }
        volume_set_group.setOnCheckedChangeListener { _, i ->
            preferencesRepository.setVolume(i)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(R.layout.fragment_volume_set)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        window.attributes = wlp
        window.attributes.windowAnimations = R.style.DialogAnimation
        return dialog
    }

}
