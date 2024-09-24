package com.example.mal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimeDetail(
    viewModel: MalViewModel,
    uiState: MalUiState
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(uiState.currentAnime?.title.toString(), modifier = Modifier.padding(16.dp))
        Text(viewModel.currentAnime?.title.toString(), modifier = Modifier.padding(16.dp))
    }
}