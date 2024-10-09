package com.example.mal.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MangaDetail(
    uiState: MalUiState,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(uiState.currentManga?.data?.images?.jpg?.image_url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(5.dp, 20.dp)
                .size(250.dp, 350.dp)
                .border(1.dp, androidx.compose.ui.graphics.Color.Black)
        )


        Text(
            text = uiState.currentManga?.data?.title ?: "null",
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.displaySmall
        )
        Text(text = uiState.currentManga?.data?.synopsis.toString() ?: "null")
    }
    Text("swag")
}