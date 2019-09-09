package io.vinter.wakemeup.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.google.firebase.messaging.FirebaseMessaging
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.preferences.PreferencesRepository
import io.vinter.wakemeup.ui.friends.FriendsFragment
import io.vinter.wakemeup.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager

    private val mOnNavigationItemSelectedListener = { item: MenuItem ->
        when (item.itemId) {
            R.id.navigation_friends -> showFragment("friends", "profile")
            R.id.navigation_profile -> showFragment("profile", "friends")
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferencesRepository = PreferencesRepository(this)
        fragmentManager = supportFragmentManager
        if ((fragmentManager.findFragmentByTag("all") == null) and (fragmentManager.findFragmentByTag("profile") == null)) {
            fragmentManager.beginTransaction()
                    .add(R.id.container_content, FriendsFragment(), "friends")
                    .add(R.id.container_content, ProfileFragment(), "profile")
                    .commit()
            fragmentManager.popBackStackImmediate()
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val id = preferencesRepository.getUserId()
        FirebaseMessaging.getInstance().subscribeToTopic(id)
                .addOnCompleteListener {
                    if (it.isSuccessful) preferencesRepository.setUserTopic(id)
                }
    }

    private fun showFragment(tagShow: String, tagHide: String) {
        fragmentManager.beginTransaction()
                .show(Objects.requireNonNull<Fragment>(fragmentManager.findFragmentByTag(tagShow)))
                .hide(Objects.requireNonNull<Fragment>(fragmentManager.findFragmentByTag(tagHide)))
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            22 -> (Objects.requireNonNull<Fragment>(fragmentManager.findFragmentByTag("friends")) as FriendsFragment).refreshFriendList()
        }
    }

}