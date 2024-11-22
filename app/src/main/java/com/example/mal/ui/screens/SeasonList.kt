package com.example.mal.ui.screens

import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mal.model.seasons.SeasonData
import com.example.mal.model.seasons.SeasonList
import com.example.mal.ui.components.ErrorScreen

@Composable
fun SeasonScreen(
    viewModel: MalViewModel,
    uiState: SeasonListUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier){

    when(uiState){
        is SeasonListUiState.Loading -> Text("Loading")
        is SeasonListUiState.Success -> {
            val seasonList = uiState.bigSeasonList
            SeasonList(seasonList)

        }
        is SeasonListUiState.Error -> ErrorScreen(retryAction = viewModel::getSeasonList)

    }

}

@Composable
fun SeasonList(
    seasonData: List<SeasonData>
){
    seasonData.forEach{
        Text(text = it.year.toString())
    }
}

@Composable
fun SeasonCard(){
    Card(modifier = Modifier){

    }
}