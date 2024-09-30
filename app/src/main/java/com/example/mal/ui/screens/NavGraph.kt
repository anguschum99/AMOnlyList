package com.example.mal.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mal.model.Anime
import com.example.mal.ui.components.BottomNavBar
import com.example.mal.ui.components.NavigationItems
import com.example.mal.ui.components.TopBar


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    viewModel: MalViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsState().value
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            TopBar(
                currentScreen = currentDestination?.route ?: "",
                navController = navController,
                canNavigateBack = currentDestination?.route == NavigationItems.Detail.route,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItems.Home.route,
        ) {


            composable(route = NavigationItems.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    uiState = viewModel.topAnimeUiState,
                    contentPaddingValues = innerPadding,
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
                    contentPaddingValues = innerPadding,
                    onClick = {
                        viewModel.updateCurrentAnime(it)
                        viewModel.currentAnime = it
                        navController.navigate(NavigationItems.Detail.route)
                    },
                )

            }

            composable(route = NavigationItems.Detail.route) {
                AnimeDetail(viewModel = viewModel, uiState = uiState,contentPaddingValues = innerPadding)
            }


        }

    }





}