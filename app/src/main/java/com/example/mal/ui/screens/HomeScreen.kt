package com.example.mal.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mal.model.Anime

@Composable
fun HomeScreen(
    viewModel: MalViewModel,
    uiState: HomeAnimeUiState,
    contentPaddingValues: PaddingValues,
    onClick: (Anime) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(contentPaddingValues)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        when (uiState) {
            is HomeAnimeUiState.Loading -> Text(text = "Loading")
            is HomeAnimeUiState.Success -> {
                Text("Top Anime")
                GenreRow(list = uiState.topAnimeList,onClick = onClick)
                Text("Top Airing")
                GenreRow(list = uiState.topAiringList,onClick = onClick)

            }
            is HomeAnimeUiState.Error -> Text(text = "Error")

        }
    }
}

@Composable
fun GenreCard(anime: Anime, onClick: (Anime) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp, 250.dp)
            .clickable { onClick(anime) },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(anime.images.jpg.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally)

            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = anime.title ?: "null",
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle.Default.copy(
                        lineBreak = LineBreak.Paragraph
                    )
                )
            }

        }

    }
}

@Composable
fun GenreRow(list: List<Anime>,onClick: (Anime) -> Unit) {
    LazyRow {
        items(list, key = { anime -> anime.mal_id }) { anime ->
            GenreCard(anime = anime, onClick = onClick)

        }
    }
}