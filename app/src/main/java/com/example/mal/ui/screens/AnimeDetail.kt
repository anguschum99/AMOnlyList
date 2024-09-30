package com.example.mal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mal.R

@Composable
fun AnimeDetail(
    viewModel: MalViewModel,
    uiState: MalUiState,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val scrollState = rememberScrollState()

    var expanded by rememberSaveable { mutableStateOf(false) }


    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.White)
            .padding(contentPaddingValues)
            .verticalScroll(scrollState)
    ) {

        Row() {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(uiState.currentAnime?.images?.jpg?.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                //contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(5.dp, 20.dp)
                    .size(250.dp, 350.dp)
                    .border(1.dp, androidx.compose.ui.graphics.Color.Black)
            )

            Spacer(Modifier.weight(0.1f))

            Column(
                Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
            ) {
                Row() {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Filled.Star,
                        contentDescription = null,
                    )

                    Spacer(Modifier.weight(0.1f))

                    Text(
                        uiState.currentAnime?.score.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(10.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(stringResource(R.string.ranking))
                    Spacer(Modifier.weight(0.1f))

                    Text("#")
                    Text(
                        uiState.currentAnime?.rank?.toString() ?: "",
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(stringResource(R.string.popularity))
                    Spacer(Modifier.weight(0.1f))

                    Text("#")
                    Text(
                        uiState.currentAnime?.popularity?.toString() ?: "",
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Spacer(Modifier.weight(0.1f))

                    Text(
                        uiState.currentAnime?.type ?: "",
                        modifier = Modifier.padding(),
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Spacer(Modifier.weight(0.1f))

                    Text(
                        uiState.currentAnime?.status ?: "",
                        modifier = Modifier.padding(),
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Spacer(Modifier.weight(0.1f))

                    Text(
                        uiState.currentAnime?.episodes.toString() ?: "",
                        modifier = Modifier.padding(),
                    )
                    Text(" Episodes")
                }

            }
        }
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                uiState.currentAnime?.title.toString(),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(Modifier.padding(10.dp))

            Text(uiState.currentAnime?.synopsis.toString())
        }
    }
}