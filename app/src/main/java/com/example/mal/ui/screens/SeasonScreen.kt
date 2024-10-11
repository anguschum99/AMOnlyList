package com.example.mal.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mal.ui.components.ErrorScreen

@Composable
fun SeasonScreen(
    viewModel: MalViewModel,
    uiState: CurrentSeasonUiState,
){
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        when (uiState) {
            is CurrentSeasonUiState.Loading -> Text("Loading")
            is CurrentSeasonUiState.Success -> {
                Text("WHAT")
                }
            is CurrentSeasonUiState.Error -> ErrorScreen(
                retryAction = viewModel::getCurrentSeason
            )
        }

    }
}