package com.androidtv.player

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.legacy.MediaSessionCompat

@UnstableApi
class MediaSessionConnector(
    context: Context,
    private val player: Player
) {
    @SuppressLint("RestrictedApi")
    private val mediaSession: MediaSessionCompat =
        MediaSessionCompat(context, "TVPlayerMediaSession").apply {
            isActive = true
        }

    private val connector = MediaSessionConnector(
        mediaSession,
        player = TODO()
    )

    init {
        connector.setPlayer(player)
    }

    fun release() {
        connector.setPlayer(null)
        mediaSession.release()
    }
}