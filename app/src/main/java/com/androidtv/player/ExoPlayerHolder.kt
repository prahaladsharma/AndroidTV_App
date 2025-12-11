package com.androidtv.player

import android.content.Context
import androidx.annotation.OptIn

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource

class ExoPlayerHolder(
    private val context: Context,
) : DefaultLifecycleObserver {

    val player: ExoPlayer by lazy {
        ExoPlayer.Builder(context).build()
    }

    @OptIn(UnstableApi::class)
    fun prepareAndPlay(url: String, playWhenReady: Boolean = true) {
        val mediaItem = MediaItem.fromUri(url)
        val dataSourceFactory = DefaultDataSource.Factory(context)
        val mediaSource: MediaSource = when {
            url.contains(".m3u8") -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
            else -> ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
        }
        player.setMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = playWhenReady
    }

    override fun onPause(owner: LifecycleOwner) {
        // Optionally pause
        player.pause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        player.release()
    }
}