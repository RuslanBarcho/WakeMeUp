package io.vinter.wakemeup.ui.request

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import io.vinter.wakemeup.R
import io.vinter.wakemeup.utils.RequestRecyclerAdapter
import kotlinx.android.synthetic.main.activity_request.*

class RequestActivity : AppCompatActivity() {

    var res = 0
    lateinit var adapter: RequestRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        val viewModel = ViewModelProviders.of(this).get(RequestViewModel::class.java)
        val recycler = request_recycler
        val preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val token = preferences.getString("token", "")!!
        if (savedInstanceState != null) res = savedInstanceState.getInt("result")

        if (viewModel.requests.value == null && !viewModel.loading) viewModel.getRequests(token)
        viewModel.requests.observe(this, Observer {
            if (it != null){
                adapter = RequestRecyclerAdapter(this, it) {position, mode ->
                    when(mode){
                        0 -> viewModel.acceptRequest(token, it[position])
                        1 -> viewModel.rejectRequest(token, it[position])
                    }
                }
                val animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
                recycler.layoutManager = LinearLayoutManager(this)
                recycler.layoutAnimation = animation
                recycler.adapter = adapter
            }
        })
        viewModel.message.observe(this, Observer {
            if (it != null){
                Toast.makeText(this, it.first.message, Toast.LENGTH_SHORT).show()
                viewModel.requests.value!!.remove(it.second)
                adapter.refresh(viewModel.requests.value!!)
                res = 22
            }
        })
        request_back.setOnClickListener {
            this.finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putInt("result", res)
    }

    override fun onStop() {
        setResult(res)
        super.onStop()
    }
}
