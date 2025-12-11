package com.androidtv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.tv.material3.Surface
import com.androidtv.player.PlayerViewModel
import com.androidtv.ui.theme.AndroidTV_AppTheme
import com.androidtv.ui.theme.PlayerScreen

class MainActivity : ComponentActivity() {

    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val testUrl = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3" // replace with HLS/MP4
            PlayerScreen(viewModel, mediaUrl = testUrl)
        }
    }

    override fun onStop() {
        super.onStop()
        // If you want playback to stop in foreground Activity:
        // viewModel.exoHolder.player.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.exoHolder.player.release()
    }


}