package com.example.mal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
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
fun AnimeDetail(
    viewModel: MalViewModel,
    uiState: MalUiState
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.White)
    ) {
        Text(uiState.currentAnime?.title.toString(), modifier = Modifier.padding(16.dp))

        Row() {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(uiState.currentAnime?.images?.jpg?.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                //contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(5.dp)
                    .size(250.dp, 350.dp )
                    .border(1.dp, androidx.compose.ui.graphics.Color.Black)
            )



            Spacer(Modifier.weight(0.1f))

            Row (Modifier.padding(16.dp)) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.Star,
                    contentDescription = null,
                )

                Text(
                    uiState.currentAnime?.score.toString(),
                    modifier = Modifier.padding(start = 5.dp, end = 20.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }


        }


    }
}