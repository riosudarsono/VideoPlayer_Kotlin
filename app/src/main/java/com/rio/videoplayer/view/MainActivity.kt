package com.rio.videoplayer.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rio.videoplayer.R
import com.rio.videoplayer.data.response.ResultResponse
import com.rio.videoplayer.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BaseApp.Listener, VideoAdapter.Listener {

    private val viewModel: MainVM by viewModels()
    private lateinit var adapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BaseApp(this).set()
    }

    override fun getIntentData() {

    }

    override fun setOnClick() {

    }

    override fun setAdapter() {
        adapter = VideoAdapter(this, ArrayList())
        rv_video.adapter = adapter
        rv_video.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun setContent() {

    }

    override fun loadData() {
        viewModel.search("jack+johnson").observe(this, Observer {
            when (it.status) {
                Status.LOADING -> progressBar.show()
                Status.SUCCESS -> {
                    progressBar.hide()
                    adapter.setItems(it.data ?: ArrayList())
                }
                Status.ERROR -> {
                    progressBar.hide()
                    toast(it.message)
                }
            }
        })
    }

    override fun onClick(data: ResultResponse) {

    }
}