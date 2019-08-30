package io.vinter.wakemeup.ui.friends

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import io.vinter.wakemeup.R
import io.vinter.wakemeup.utils.PairRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendsFragment : Fragment() {

    private lateinit var viewModel: FriendsViewModel
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(FriendsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = context!!.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val recycler = pairs_recycler

        if (viewModel.friends.value == null && !viewModel.loading) viewModel.getFiends(preferences.getString("token", "")!!, context!!)
        viewModel.friends.observe(this, Observer {
            if (it != null){
                val adapter = PairRecyclerAdapter(context!!, it) {id -> viewModel.sendCall(preferences.getString("token", "")!!, id)}
                val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
                recycler.layoutManager = LinearLayoutManager(context)
                recycler.layoutAnimation = animation
                recycler.adapter = adapter
            }
        })

        viewModel.messages.observe(this, Observer {
            if (it != null){
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                viewModel.messages.postValue(null)
            }
        })

        friends_add.setOnClickListener {
            val addFriendsFragment = AddFriendFragment()
            addFriendsFragment.setTargetFragment(this, 0)
            addFriendsFragment.show(fragmentManager, "add_friend_dialog")
        }

        viewModel.error.observe(this, Observer {
            if (it != null) {
                val snackBar = Snackbar.make(friends_snackbar, it, Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(ContextCompat.getColor(context!!,R.color.colorAccent))
                snackBar.setAction("Retry") {
                    snackBar.dismiss()
                    viewModel.getFiends(preferences.getString("token", "")!!, context!!)
                }
                snackBar.show()
                viewModel.error.postValue(null)
            }
        })
    }

    fun addFriend(query: String){
        viewModel.sendRequest(preferences.getString("token", "")!!, query)
    }

    fun refreshFriendList(){
        viewModel.getFiends(preferences.getString("token", "")!!, context!!)
    }

}
