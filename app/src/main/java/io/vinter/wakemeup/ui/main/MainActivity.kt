package io.vinter.wakemeup.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import io.vinter.wakemeup.R
import io.vinter.wakemeup.ui.friends.FriendsFragment
import io.vinter.wakemeup.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var preferences: SharedPreferences

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
        preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        fragmentManager = supportFragmentManager
        if ((fragmentManager.findFragmentByTag("all") == null) and (fragmentManager.findFragmentByTag("profile") == null)) {
            fragmentManager.beginTransaction()
                    .add(R.id.container_content, FriendsFragment(), "friends")
                    .add(R.id.container_content, ProfileFragment(), "profile")
                    .commit()
            fragmentManager.popBackStackImmediate()
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        FirebaseMessaging.getInstance().subscribeToTopic(preferences.getString("id", ""))
                .addOnCompleteListener { task ->
                    var msg = getString(R.string.firebase_success)
                    if (!task.isSuccessful) msg = getString(R.string.firebase_error)
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
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