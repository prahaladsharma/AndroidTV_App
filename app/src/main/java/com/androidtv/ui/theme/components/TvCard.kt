package com.androidtv.ui.theme.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.androidtv.ui.theme.AndroidTV_AppTheme

@Composable
fun TvCard(
    label: String,
    modifier: Modifier = Modifier
){
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.08f else 1.0f)

    Card(
        modifier = modifier
            .size(width = 220.dp, height = 140.dp)
            .onFocusChanged { focused = it.isFocused }
            .scale(scale),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (focused) 12.dp else 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = label, color = Color.White, modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TvCardFocusedPreview() {
    AndroidTV_AppTheme {
        // To preview the focused state, we can simulate it by
        // slightly modifying the original composable for the preview.
        // Here's a simple way to do it without changing your actual component:
        val scale by animateFloatAsState(1.08f) // Simulate focus scale

        Card(
            modifier = Modifier
                .size(width = 220.dp, height = 140.dp)
                .scale(scale),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp), // Simulate focus elevation
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = "Movies Card", color = Color.White, modifier = Modifier.padding(8.dp))
            }
        }
    }
}