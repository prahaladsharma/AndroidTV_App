package com.androidtv.player

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.legacy.MediaBrowserCompat
import androidx.media3.session.legacy.MediaBrowserServiceCompat
import androidx.media3.session.legacy.MediaSessionCompat

/**
 * Minimal MediaBrowserServiceCompat implementation so the app can be browsed by system UI.
 * This service is optional for simple foreground-only playback but helpful for background/OS integration.
 */
@SuppressLint("RestrictedApi")
@OptIn(UnstableApi::class)
class MediaPlaybackService : MediaBrowserServiceCompat() {

    private lateinit var mediaSession: MediaSessionCompat
    private var player: ExoPlayer? = null
    private var connector: MediaSessionConnector? = null

    @SuppressLint("RestrictedApi")
    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "TvExoPlayerService").apply {
            isActive = true
        }
        sessionToken = mediaSession.sessionToken

        // Basic ExoPlayer instance for service
        player = ExoPlayer.Builder(this).build()
        connector = MediaSessionConnector(mediaSession).also { it.setPlayer(player) }
    }

    override fun onDestroy() {
        connector?.setPlayer(null)
        connector = null
        player?.release()
        player = null
        mediaSession.release()
        super.onDestroy()
    }

    override fun onGetRoot(
        clientPackageName: String?,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        // Allow browsing - provide a simple root
        return BrowserRoot("root", null)
    }

    override fun onLoadChildren(
        parentId: String?,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        // No browsable content in this sample
        result.sendResult(mutableListOf())
    }
}