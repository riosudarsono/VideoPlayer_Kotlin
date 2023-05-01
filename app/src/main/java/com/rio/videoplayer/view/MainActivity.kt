package com.rio.videoplayer.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.rio.videoplayer.R
import com.rio.videoplayer.data.response.ResultResponse
import com.rio.videoplayer.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BaseApp.Listener, VideoAdapter.Listener {

    private val viewModel: MainVM by viewModels()
    private lateinit var adapter: VideoAdapter

    private lateinit var simpleExoPlayer: ExoPlayer
    private lateinit var mediaDataSourceFactory: DataSource.Factory
    private  var linkVideo: String = ""

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
        if (linkVideo.isNotEmpty()) releasePlayer()
        linkVideo = data.previewUrl ?: ""
        initializePlayer()
        playerView.show()
    }

    private fun initializePlayer() {
        mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"))

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
            MediaItem.fromUri(linkVideo ?: ""))

        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        simpleExoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        simpleExoPlayer.addMediaSource(mediaSource)
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.repeatMode = Player.REPEAT_MODE_OFF;
        simpleExoPlayer.prepare()

        playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        playerView.player = simpleExoPlayer
        playerView.requestFocus()
    }

    private fun releasePlayer() {
        simpleExoPlayer.release()
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) releasePlayer()
    }
}