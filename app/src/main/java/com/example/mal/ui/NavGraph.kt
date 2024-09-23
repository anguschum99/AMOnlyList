package com.example.mal.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mal.data.ScreenType
import com.example.mal.ui.components.NavigationItems


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    viewModel: MalViewModel,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavigationItems.Home.route,
    ) {
        composable(route = NavigationItems.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                uiState = viewModel.topAnimeUiState,
                contentPaddingValues = contentPaddingValues
            )
        }
        composable(route = NavigationItems.Search.route) {
            SearchScreen(
                viewModel = viewModel,
                uiState = viewModel.animeUiState,
                modifier = modifier,
                contentPaddingValues = contentPaddingValues
            )

        }
    }
}