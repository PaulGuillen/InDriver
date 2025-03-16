package com.devpaul.indriver.presentation.navigation.graph.client

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devpaul.indriver.presentation.navigation.Graph
import com.devpaul.indriver.presentation.navigation.graph.profile.profileNavGraph
import com.devpaul.indriver.presentation.navigation.screen.client.ClientScreen
import com.devpaul.indriver.presentation.screens.client.mapSearcher.MapSearcherScreen

@Composable
fun ClientNavGraph(navHostController: NavHostController) {
    NavHost(
        navController  = navHostController,
        route = Graph.CLIENT,
        startDestination = ClientScreen.MapSearcher.route
    ) {
        composable(ClientScreen.MapSearcher.route) {
            MapSearcherScreen(navHostController = navHostController)
        }
        profileNavGraph(navHostController)
    }
}