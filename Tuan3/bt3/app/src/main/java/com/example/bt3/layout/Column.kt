package com.example.bt3.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColumnScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Column Component",
            fontSize = 24.sp
        )

        Column(modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "Item 1", fontSize = 16.sp)
            Text(text = "Item 2", fontSize = 16.sp)
            Text(text = "Item 3", fontSize = 16.sp)
        }
    }
}