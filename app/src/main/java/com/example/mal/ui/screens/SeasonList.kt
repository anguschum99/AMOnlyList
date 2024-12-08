package com.example.mal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mal.model.seasons.SeasonData
import com.example.mal.model.seasons.SeasonList
import com.example.mal.ui.components.ErrorScreen

@Composable
fun SeasonScreen(
    viewModel: MalViewModel,
    uiState: SeasonListUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    when (uiState) {
        is SeasonListUiState.Loading -> Text("Loading")
        is SeasonListUiState.Success -> {
            val seasonList = uiState.bigSeasonList
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                SeasonList(seasonData = seasonList)
            }

        }

        is SeasonListUiState.Error -> Text("fail")

    }

}

@Composable
fun SeasonList(
    seasonData: List<SeasonData>
) {
    LazyColumn {
        items(seasonData.size) {
            SeasonCard(seasonData = seasonData[it])
        }
    }
}

@Composable
fun SeasonCard(
    seasonData: SeasonData
) {
    Text(seasonData.year.toString())
    Card(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
    ) {
        LazyRow(modifier = Modifier.padding(0.dp)) {
            items(seasonData.seasons) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(text = it)
                }
            }
        }
    }
}