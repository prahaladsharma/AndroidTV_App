package com.androidtv.ui.theme

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidtv.ui.theme.components.TvCard

@Composable
fun HomeScreen(){
    val rows = remember { (1..8).map { row -> List(8) { "Item $row-${it + 1}" } } }
    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        itemsIndexed(rows) { rowIndex, rowItems ->
            RowBlock(
                title = "Row ${rowIndex + 1}",
                items = rowItems,
                rowIndex = rowIndex,
                rowsCount = rows.size,
                focusManager = focusManager
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun RowBlock(
    title: String,
    items: List<String>,
    rowIndex: Int,
    rowsCount: Int,
    focusManager: FocusManager,
){
    Column {
        Text(text = title, fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))


        // We'll set up a FocusRequester array for items to support wrapping programmatic focus
        val itemRequesters = remember { List(items.size) { FocusRequester() } }

        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
        ) {
            itemsIndexed(items) { index, label ->
                val focusRequester = itemRequesters[index]
                TvCard(
                    label = label,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .focusRequester(focusRequester)
                        .onKeyEvent { keyEvent ->
                            // Intercept DPAD left/right to implement wrapping
                            if (keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                                when (keyEvent.nativeKeyEvent.keyCode) {
                                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                                        if (index == 0) {
                                            // Wrap to last
                                            itemRequesters.last().requestFocus()
                                            return@onKeyEvent true
                                        }
                                    }

                                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                                        if (index == items.lastIndex) {
                                            // Wrap to first
                                            itemRequesters.first().requestFocus()
                                            return@onKeyEvent true
                                        }
                                    }

                                    KeyEvent.KEYCODE_DPAD_UP -> {
                                        // Move focus up to previous row if exists
                                        if (rowIndex > 0) {
                                            focusManager.moveFocus(FocusDirection.Up)
                                            return@onKeyEvent true
                                        }
                                    }

                                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                                        if (rowIndex < rowsCount - 1) {
                                            focusManager.moveFocus(FocusDirection.Down)
                                            return@onKeyEvent true
                                        }
                                    }
                                }
                            }
                            false
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1920, heightDp = 1080)
@Composable
fun HomeScreenPreview() {
    AndroidTV_AppTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun RowBlockPreview() {
    // Dummy data for the preview
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
    val focusManager = LocalFocusManager.current

    AndroidTV_AppTheme {
        // We add a background to see the white text clearly
        Column(modifier = Modifier.background(Color.Black).padding(24.dp)) {
            RowBlock(
                title = "Sample Row",
                items = items,
                rowIndex = 0,
                rowsCount = 5,
                focusManager = focusManager
            )
        }
    }
}

