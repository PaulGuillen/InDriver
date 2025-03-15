package com.devpaul.indriver.presentation.navigation.graph.client

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.devpaul.indriver.presentation.navigation.Graph
import com.devpaul.indriver.presentation.navigation.screen.client.ClientScreen
import com.devpaul.indriver.presentation.screens.client.home.ClientHomeScreen
import com.devpaul.indriver.presentation.screens.client.mapSearcher.MapSearcherScreen
import com.devpaul.indriver.presentation.screens.profile.info.ProfileInfoScreen

fun NavGraphBuilder.clientNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.CLIENT,
        startDestination = ClientScreen.ClientHome.route
    ) {
        composable(ClientScreen.ClientHome.route) {
            ClientHomeScreen(navHostController = navHostController)
        }
        composable(ClientScreen.ProfileInfo.route) {
            ProfileInfoScreen(navHostController = navHostController)
        }
        composable(ClientScreen.MapSearcher.route) {
            MapSearcherScreen(navHostController = navHostController)
        }

    }
}