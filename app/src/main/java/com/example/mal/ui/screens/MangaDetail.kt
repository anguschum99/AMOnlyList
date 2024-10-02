package com.example.mal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MangaDetail(
    uiState: MalUiState,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier) {
        Text(text = uiState.currentManga?.data?.title ?: "null")
        Text(text = uiState.currentManga?.data?.synopsis.toString() ?: "null")
    }
    Text("swag")
}