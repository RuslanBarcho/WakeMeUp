package io.vinter.wakemeup.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.bitmap.CircleCrop

import io.vinter.wakemeup.R
import io.vinter.wakemeup.service.AlarmService
import io.vinter.wakemeup.ui.request.RequestActivity
import io.vinter.wakemeup.ui.settings.SettingsActivity
import io.vinter.wakemeup.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = context!!.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        GlideApp.with(context!!)
                .load(preferences.getString("pictureURL", ""))
                .override(300, 300)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .transform(CircleCrop())
                .into(profile_picture)

        profile_checkout.setOnClickListener {
            context!!.stopService(Intent(activity!!.applicationContext, AlarmService::class.java))
        }

        profile_requests.setOnClickListener {
            activity!!.startActivityForResult(Intent(activity, RequestActivity::class.java), 22)
        }

        profile_settings.setOnClickListener {
            activity!!.startActivityForResult(Intent(activity, SettingsActivity::class.java), 23)
        }
    }

}