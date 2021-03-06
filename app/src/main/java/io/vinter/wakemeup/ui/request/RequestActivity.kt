package io.vinter.wakemeup.ui.request

import androidx.lifecycle.Observer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import io.vinter.wakemeup.R
import io.vinter.wakemeup.utils.RequestRecyclerAdapter
import kotlinx.android.synthetic.main.activity_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RequestActivity : AppCompatActivity() {

    var res = 0
    private val viewModel: RequestViewModel by viewModel()
    private lateinit var adapter: RequestRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        val recycler = request_recycler

        if (savedInstanceState != null) res = savedInstanceState.getInt("result")
        if (viewModel.state.value is RequestState.Initial) viewModel.getRequests()

        viewModel.state.observe(this, Observer {
            when (it){
                is RequestState.Loading -> {
                    setVisibility(View.VISIBLE, View.GONE, View.GONE)
                }
                is RequestState.Success -> {
                    setVisibility(View.GONE, View.GONE, View.GONE)
                    adapter = RequestRecyclerAdapter(this, it.data) {position, mode ->
                        when(mode){
                            0 -> viewModel.acceptRequest(it.data[position])
                            1 -> viewModel.rejectRequest(it.data[position])
                        }
                    }
                    recycler.layoutManager = LinearLayoutManager(this)
                    recycler.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
                    recycler.adapter = adapter
                }
                is RequestState.Error -> {
                    request_error.setErrorMessage(getString(R.string.unable_load_requests))
                    request_error.setOnRetryListener { viewModel.getRequests() }
                    setVisibility(View.GONE, View.VISIBLE, View.GONE)
                }
                is RequestState.Empty ->  {
                    setVisibility(View.GONE, View.GONE, View.VISIBLE)
                }
            }
        })

        viewModel.message.observe(this, Observer {
            if (it != null && viewModel.state.value is RequestState.Success){
                Toast.makeText(this, it.first.message, Toast.LENGTH_SHORT).show()
                (viewModel.state.value as RequestState.Success).data.remove(it.second)
                adapter.refresh((viewModel.state.value as RequestState.Success).data)
                res = 22
            }
        })

        request_back.setOnClickListener { this.finish() }
    }

    private fun setVisibility(loader: Int, error: Int, emptyView: Int){
        request_loader.visibility = loader
        request_error.visibility = error
        request_empty_text.visibility = emptyView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("result", res)
    }

    override fun onStop() {
        setResult(res)
        super.onStop()
    }
}
