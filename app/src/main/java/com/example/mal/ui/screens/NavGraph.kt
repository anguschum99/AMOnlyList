package com.example.mal.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mal.model.Anime
import com.example.mal.ui.components.NavigationItems


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    viewModel: MalViewModel,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
) {
    val uiState = viewModel.uiState.collectAsState().value


    NavHost(
        navController = navController,
        startDestination = NavigationItems.Home.route,
    ) {

        composable(route = NavigationItems.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                uiState = viewModel.topAnimeUiState,
                contentPaddingValues = contentPaddingValues,
                onClick = {
                    viewModel.updateCurrentAnime(it)
                    viewModel.currentAnime = it
                    navController.navigate(NavigationItems.Detail.route)
                },

                )
        }
        composable(route = NavigationItems.Search.route) {
            SearchScreen(
                viewModel = viewModel,
                uiState = viewModel.animeUiState,
                modifier = modifier,
                contentPaddingValues = contentPaddingValues,
                onClick = {
                    viewModel.updateCurrentAnime(it)
                    viewModel.currentAnime = it
                    navController.navigate(NavigationItems.Detail.route)
                },
            )

        }

        composable(route = NavigationItems.Detail.route) {
            AnimeDetail(viewModel = viewModel, uiState = uiState,contentPaddingValues = contentPaddingValues)
        }


    }


}