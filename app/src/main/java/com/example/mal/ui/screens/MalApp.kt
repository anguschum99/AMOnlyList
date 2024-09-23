package com.example.mal.ui.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mal.ui.components.BottomNavBar


@Composable
fun MalApp(
    viewModel: MalViewModel = viewModel(factory = MalViewModel.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val paddingValues = WindowInsets.navigationBars.asPaddingValues()
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        BottomNavGraph(
            navController = navController,
            viewModel = viewModel,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPaddingValues = innerPadding
        )
    }
}
