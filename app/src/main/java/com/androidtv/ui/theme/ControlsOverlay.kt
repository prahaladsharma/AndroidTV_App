package com.androidtv.ui.theme

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.media3.common.Player

@Composable
fun ControlsOverlay(player: Player) {
    var isVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(modifier = Modifier
            .padding(24.dp)
            .focusable(true),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                if (player.isPlaying) player.pause() else player.play()
            }) {
                Text(if (player.isPlaying) "Pause" else "Play")
            }

            Button(onClick = {
                val newPos = (player.currentPosition - 10_000).coerceAtLeast(0)
                player.seekTo(newPos)
            }) {
                Text("<< 10s")
            }

            Button(onClick = {
                val newPos = (player.currentPosition + 10_000).coerceAtMost(player.duration.coerceAtLeast(0))
                player.seekTo(newPos)
            }) {
                Text("10s >>")
            }
        }
    }
}