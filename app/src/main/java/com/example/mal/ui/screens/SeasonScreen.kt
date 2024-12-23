package com.example.mal.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mal.model.seasons.Data
import com.example.mal.model.seasons.SeasonList
import com.example.mal.ui.components.ErrorScreen

@Composable
fun SeasonScreen(
    viewModel: MalViewModel,
    uiState: CurrentSeasonUiState,
    onClick: (Data) -> Unit,
    seasonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val number = 1
    var pager by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {

        when (uiState) {
            is CurrentSeasonUiState.Loading -> Text("Loading")
            is CurrentSeasonUiState.Success -> {
                val lastVisiblePage = uiState.seasonList.pagination.last_visible_page

                Column(
                    modifier = modifier
                        .weight(1f)

                ) {
                    Button(
                        onClick = { seasonClick() },
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(text = "Season List")
                    }

                    PageButtons(
                        uiState,
                        nextClick = {
                            pager = updatePageNumber(pager, lastVisiblePage)
                            viewModel.getCurrentSeason(pager)
                        },
                        previousClick = {
                            pager = decreasePageNumber(pager)
                            viewModel.getCurrentSeason(pager)
                        },
                        currentSeasonClick = {
                            pager = number
                            viewModel.getCurrentSeason(1)
                        },
                        currentPage = pager
                    )
                    SeasonGrid(uiState.seasonList.data, onClick = onClick)
                }
            }

            is CurrentSeasonUiState.Error -> ErrorScreen(
                retryAction = viewModel::getCurrentSeason
            )
        }
    }
}

fun decreasePageNumber(page: Int): Int {
    val previousPage = page - 1
    return if (previousPage < 1) {
        1
    } else {
        return previousPage
    }
}

fun updatePageNumber(page: Int, maximum: Int): Int {
    val nextPage = page + 1
    return if (page < 1) {
        1
    } else if (page > maximum) {
        maximum
    } else {
        return nextPage
    }
}



@Composable
fun PageButtons(
    uiState: CurrentSeasonUiState,
    nextClick: () -> Unit,
    previousClick: () -> Unit,
    currentSeasonClick: () -> Unit,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {


        Button(
            onClick = { previousClick() },
            enabled =
            if (currentPage > 1) {
                true
            } else
                false

        ) {
            Text(text = "Previous")
        }

        Button(onClick = { currentSeasonClick() }) {
            Text(text = "Current Season")
        }
        Button(onClick = { nextClick() }) {
            Text(text = "Next")
        }
    }
}
//

@Composable
fun SeasonCard(data: Data, onClick: (Data) -> Unit) {
    Card(modifier = Modifier
        .padding(5.dp),
        onClick = { onClick(data) }) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(data.images.jpg.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    //.padding(5.dp, 20.dp)
                    .size(250.dp, 350.dp)
                    .border(1.dp, androidx.compose.ui.graphics.Color.Black)
            )
            Text(data.title, modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun SeasonGrid(
    data: List<Data>,
    onClick: (Data) -> Unit
) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items = data, key = { data -> data.mal_id }) { data ->
                SeasonCard(data = data, onClick = { onClick(data) })

            }

        }
    }
}