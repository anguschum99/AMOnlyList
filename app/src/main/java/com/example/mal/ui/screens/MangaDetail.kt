package com.example.mal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MangaDetail(
    uiState: MalUiState,
    modifier: Modifier = Modifier
){
    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(uiState.currentManga?.data?.images?.jpg?.image_url)
                .crossfade(true)
                .build(),
            contentDescription = null
        )


        Text(text = uiState.currentManga?.data?.title ?: "null")
        Text(text = uiState.currentManga?.data?.synopsis.toString() ?: "null")
    }
    Text("swag")
}