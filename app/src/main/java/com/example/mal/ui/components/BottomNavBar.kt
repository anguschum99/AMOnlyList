package com.example.mal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class NavigationItems(
    val route:String,
    val title:String,
    val selectedIcon:ImageVector,
    val unselectedIcon:ImageVector
) {
    object Home : NavigationItems(
        route = "home",
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    object Search : NavigationItems(
        route = "search",
        title = "Search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )
    object Seasonal : NavigationItems(
        route = "seasonal",
        title = "Seasonal",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange
    )
    object Detail : NavigationItems(
        route = "detail",
        title = "Detail",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
}

@Composable
fun topNavBar(
    navController: NavHostController,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){

}


@Composable
fun BottomNavBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    val screens = listOf(
        NavigationItems.Home,
        NavigationItems.Search,
        NavigationItems.Seasonal
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        windowInsets = androidx.compose.foundation.layout.WindowInsets(
            left = 0.dp,
            top = 0.dp,
            right = 0.dp,
            bottom = 0.dp
        ),
        tonalElevation = 1.dp
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }

    }
}

@Composable
fun RowScope.AddItem(
    screen: NavigationItems,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    NavigationBarItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = if (currentDestination?.route == screen.route) {
                    screen.selectedIcon
                } else {
                    screen.unselectedIcon
                },
                contentDescription = null
            )
        },
        selected = currentDestination?.route == screen.route,
        onClick = {
            navController.navigate(screen.route)
        },
    )


}

@Preview(showBackground = true)
@Composable
fun BottomNavPreview(){

    Column {
        Text("swag")
        BottomNavBar(navController = NavHostController(LocalContext.current))
    }



}