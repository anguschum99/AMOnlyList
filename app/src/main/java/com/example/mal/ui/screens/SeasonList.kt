package com.example.mal.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mal.ui.components.ErrorScreen

@Composable
fun SeasonList(
    viewModel: MalViewModel,
    uiState: SeasonListUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier){

    when(uiState){
        is SeasonListUiState.Loading -> Text("Loading")
        is SeasonListUiState.Success -> {
            val seasonList = uiState.bigSeasonList

        }
        is SeasonListUiState.Error -> ErrorScreen(retryAction = viewModel::getSeasonList)

    }

}