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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.mal.model.AniChara
import com.example.mal.model.anime.Entry
import com.example.mal.model.anime.Relation
import com.example.mal.ui.components.ErrorScreen

@Composable
fun AnimeDetail(
    viewModel: MalViewModel,
    uiState: MalUiState,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp),
    retryAction : () -> Unit
) {
    val scrollState = rememberScrollState()

    var expanded by rememberSaveable { mutableStateOf(false) }

    when (uiState.currentAnime) {
        null -> ErrorScreen(retryAction = retryAction)
    }

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
                    .data(uiState.currentAnime?.data?.images?.jpg?.image_url ?: "")
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
                        uiState.currentAnime?.data?.score.toString() ?: "",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(10.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(stringResource(R.string.ranking))
                    Spacer(Modifier.weight(0.1f))

                    Text("#")
                    Text(
                        uiState.currentAnime?.data?.rank?.toString() ?: "",
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(stringResource(R.string.popularity))
                    Spacer(Modifier.weight(0.1f))

                    Text("#")
                    Text(
                        uiState.currentAnime?.data?.popularity?.toString() ?: "",
                        modifier = Modifier.padding(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Spacer(Modifier.weight(0.1f))

                    Text(
                        uiState.currentAnime?.data?.type ?: "",
                        modifier = Modifier.padding(),
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Spacer(Modifier.weight(0.1f))

                    Text(
                        uiState.currentAnime?.data?.status ?: "",
                        modifier = Modifier.padding(),
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Spacer(Modifier.weight(0.1f))

                    Text(
                        uiState.currentAnime?.data?.episodes.toString() ?: "",
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
                uiState.currentAnime?.data?.title.toString() ?: "",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(Modifier.padding(10.dp))

            Text(
                uiState.currentAnime?.data?.synopsis?: "",
                modifier = Modifier.padding(10.dp)
            )

            HorizontalDivider(Modifier.padding(10.dp))

        }

        Column {
            if (uiState.currentAnimeCharacters.isEmpty()) {
                Text("No characters found")
            }

            CharacterList(list = uiState.currentAnimeCharacters)
        }

        HorizontalDivider(Modifier.padding(10.dp))

        Column {
            if (uiState.currentAnime?.data?.relations?.isEmpty() == true) {
                Text("No relations found")
            }
            else{
                Text(
                    uiState.currentAnime?.data?.relations?.get(0)?.relation.toString() ?: "",
                    modifier = Modifier.padding(10.dp)

                )
                val relations = uiState.currentAnime?.data?.relations?.size ?: 0
                RelationList(list = uiState.currentAnime?.data?.relations?.subList(0, relations))

            }

        }

    }
}

@Composable
fun CharacterCard(aniChara: AniChara) {
    Card(
        modifier = Modifier
            .size(170.dp, 300.dp)
            .padding(horizontal = 2.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(aniChara.character.images.jpg.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(aniChara.role)
            Text(aniChara.character.name, modifier = Modifier)
        }
    }
}

@Composable
fun CharacterList(list: List<AniChara>) {
    LazyRow(
        Modifier
            .fillMaxWidth()
            .size(150.dp, 250.dp)
    ) {
        items(list, key = { list -> list.character.mal_id }) { list ->
            CharacterCard(list)
        }
    }
}

@Composable
fun RelationList(list: List<Relation>?){
    LazyRow {
        if (list != null) {
            items(list, key = { list -> list.entry[0].mal_id }) { list ->
                RelationshipCard(list.entry[0])
            }
        }
    }
}

@Composable
fun RelationshipCard(entry: Entry){
    Card(modifier = Modifier.size(150.dp, 100.dp)) {
        Text(entry.type)

        Text(entry.name)
    }
}