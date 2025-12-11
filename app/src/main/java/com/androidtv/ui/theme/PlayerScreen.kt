package com.androidtv.ui.theme

import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.ui.PlayerView
import com.androidtv.player.PlayerViewModel

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = viewModel(),
    mediaUrl: String
) {
    var player = viewModel.exoHolder.player

    LaunchedEffect(mediaUrl) {
        viewModel.playUrl(mediaUrl)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    useController = false // we'll provide custom controls in Compose
                    player = player
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Overlay Compose controls (seek, play/pause)
        ControlsOverlay(player = player)
    }
}