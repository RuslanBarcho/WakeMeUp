package io.vinter.wakemeup.ui.friends

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import io.vinter.wakemeup.R
import io.vinter.wakemeup.utils.PairRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_friends.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendsFragment : Fragment() {

    private lateinit var adapter: PairRecyclerAdapter
    private val viewModel: FriendsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.state.value is FriendsState.Initial) viewModel.getFiends()

        viewModel.state.observe(this, Observer {
            when (it) {
                is FriendsState.Loading -> {
                    configureRefresh(false, enabled = false)
                    configureVisibility(View.GONE, View.GONE, View.VISIBLE)
                }
                is FriendsState.Success -> {
                    configureRefresh(false, enabled = true)
                    configureVisibility(View.VISIBLE, View.GONE, View.GONE)
                    pairs_recycler.layoutManager = LinearLayoutManager(context)
                    pairs_recycler.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
                    adapter = PairRecyclerAdapter(it.friends, {
                        id -> viewModel.sendCall(id)
                    }, {
                        friend -> viewModel.deleteFriend(friend)
                    })
                    pairs_recycler.adapter = adapter
                }
                is FriendsState.Error -> {
                    configureRefresh(false, enabled = false)
                    configureVisibility(View.GONE, View.VISIBLE, View.GONE)
                    friends_error.setErrorMessage(getString(R.string.unable_load_friends))
                    friends_error.setOnRetryListener { viewModel.getFiends() }
                }
            }
        })

        friends_refresh.setOnRefreshListener { refreshFriendList() }

        friends_add.setOnClickListener {
            val addFriendsFragment = AddFriendFragment()
            addFriendsFragment.setTargetFragment(this, 0)
            addFriendsFragment.show(fragmentManager!!, "add_friend_dialog")
        }

        viewModel.messages.observe(this, Observer {
            if (it != null) {
                Toast.makeText(context, it.getLocalizedMessage(context), Toast.LENGTH_SHORT).show()
                viewModel.messages.postValue(null)
            }
        })

        viewModel.error.observe(this, Observer {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.error.postValue(null)
            }
        })

        viewModel.deletedFriend.observe(this, Observer {
            if (it != null) {
                adapter.remove(it)
                viewModel.deletedFriend.postValue(null)
            }
        })
    }

    private fun configureVisibility(recycler: Int, error: Int, loader: Int){
        pairs_recycler.visibility = recycler
        friends_loader.visibility = loader
        friends_error.visibility = error
    }

    private fun configureRefresh(refresh: Boolean, enabled: Boolean){
        friends_refresh.isRefreshing = refresh
        friends_refresh.isEnabled = enabled
    }

    fun addFriend(query: String) = viewModel.sendRequest(query)

    fun refreshFriendList() = viewModel.getFiends()

}
