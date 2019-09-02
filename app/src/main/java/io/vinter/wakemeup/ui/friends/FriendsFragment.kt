package io.vinter.wakemeup.ui.friends

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import io.vinter.wakemeup.R
import io.vinter.wakemeup.data.PreferencesRepository
import io.vinter.wakemeup.utils.PairRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendsFragment : Fragment() {

    private lateinit var viewModel: FriendsViewModel
    private lateinit var preferences: PreferencesRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(FriendsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = PreferencesRepository(context!!)

        if (viewModel.state.value !is FriendsState.Loading && viewModel.state.value !is FriendsState.Success)
            viewModel.getFiends(preferences.getToken())

        viewModel.state.observe(this, Observer {
            when (it) {
                is FriendsState.Loading -> {
                    friends_loader.visibility = View.VISIBLE
                    friends_error.visibility = View.GONE
                }
                is FriendsState.Success -> {
                    friends_loader.visibility = View.GONE
                    friends_error.visibility = View.GONE
                    val adapter = PairRecyclerAdapter(context!!, it.friends) {id -> viewModel.sendCall(preferences.getToken(), id)}
                    val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
                    pairs_recycler.layoutManager = LinearLayoutManager(context)
                    pairs_recycler.layoutAnimation = animation
                    pairs_recycler.adapter = adapter
                }
                is FriendsState.Error -> {
                    friends_loader.visibility = View.GONE
                    friends_error.visibility = View.VISIBLE
                    friends_error.setOnRetryListener { viewModel.getFiends(preferences.getToken()) }
                }
            }
        })

        viewModel.messages.observe(this, Observer {
            if (it != null) {
                Toast.makeText(context, it.getLocalizedMessage(context), Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.error.postValue(null)
            }
        })
    }

    fun addFriend(query: String){
        viewModel.sendRequest(preferences.getToken(), query)
    }

    fun refreshFriendList(){
        viewModel.getFiends(preferences.getToken())
    }

}
