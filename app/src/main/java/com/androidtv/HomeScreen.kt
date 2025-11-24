package com.androidtv

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class TvItem(val id: String, val title: String, val imageUrl: String)

val sampleItems = List(10) { idx ->
    TvItem(
        id = idx.toString(),
        title = "Sample $idx",
        imageUrl = "https://picsum.photos/seed/tv$idx/600/340"
    )
}

@Composable
fun HomeScreen(onItemClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Featured", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        // Row of focusable cards
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(sampleItems) { item ->
                TvCard(item = item, onClick = { onItemClick(item.id) })
            }
        }
    }
}

@Composable
fun TvCard(item: TvItem, onClick: () -> Unit) {
    // track focus
    var focused by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (focused) 1.05f else 1.0f)

    Card(
        modifier = Modifier
            .width(420.dp)
            .height(240.dp)
            .scale(scale)
            .focusable()
            .onPreviewKeyEvent { keyEvent ->
                // allow d-pad select (center / enter)
                if (keyEvent.type == KeyEventType.KeyUp &&
                    (keyEvent.key == Key.Enter || keyEvent.key == Key.NumPadEnter || keyEvent.key == Key.DirectionCenter)
                ) {
                    onClick()
                    true
                } else false
            }
            .onFocusChanged { state -> focused = state.isFocused },
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                    .padding(6.dp)
            ) {
                Text(item.title, color = Color.White)
            }
        }
    }
}