package com.spoton.com.presentation.ui.gallery

import android.content.Context
import android.media.MediaCas
import android.net.Uri
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class VideoPlayManager(private val context: Context, private val playerListener: Player.Listener) {
    val player: SimpleExoPlayer by lazy {
        SimpleExoPlayer.Builder(context).build().apply {
            addListener(playerListener)
        }
    }
    private val concatenatingMediaSource = ConcatenatingMediaSource()

    init {
        player.playWhenReady = true
        player.repeatMode = SimpleExoPlayer.REPEAT_MODE_OFF
        player.setMediaSource(concatenatingMediaSource)
        player.prepare()
    }

    fun playVideos(uris: List<Uri>) {
        concatenatingMediaSource.clear()
        val dataSourceFactory = DefaultDataSourceFactory(context, "exoplayer")
        uris.forEach { uri ->
            val mediaItem = MediaItem.fromUri(uri)
            val mediaSource: MediaSource =
                ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
            concatenatingMediaSource.addMediaSource(mediaSource)
        }
    }

    fun start() {
        player.playWhenReady = true
    }

    fun stop() {
        player.playWhenReady = false
    }

    fun release() {
        player.release()
    }
}
