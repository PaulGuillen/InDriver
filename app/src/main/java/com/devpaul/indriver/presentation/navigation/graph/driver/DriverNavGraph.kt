package com.devpaul.indriver.presentation.navigation.graph.driver

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devpaul.indriver.presentation.navigation.Graph
import com.devpaul.indriver.presentation.navigation.graph.profile.profileNavGraph
import com.devpaul.indriver.presentation.navigation.screen.driver.DriverScreen
import com.devpaul.indriver.presentation.screens.driver.mapMyLocation.DriverMapMyLocationScreen

@Composable
fun DriverNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.DRIVER,
        startDestination = DriverScreen.MapMyLocation.route,
    ) {
        composable(
            route = DriverScreen.MapMyLocation.route
        ) {
            DriverMapMyLocationScreen(navHostController = navHostController)
        }
        profileNavGraph(navHostController = navHostController)
    }
}