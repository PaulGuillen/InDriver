package com.devpaul.indriver.presentation.navigation.graph.roles

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.devpaul.indriver.presentation.navigation.Graph
import com.devpaul.indriver.presentation.navigation.screen.roles.RolesScreen
import com.devpaul.indriver.presentation.screens.client.home.ClientHomeScreen
import com.devpaul.indriver.presentation.screens.driver.home.DriverHomeScreen
import com.devpaul.indriver.presentation.screens.roles.RolesScreen


fun NavGraphBuilder.rolesNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = RolesScreen.Roles.route,
        route = Graph.ROLES
    ) {
        composable(RolesScreen.Roles.route) {
            RolesScreen(navHostController = navHostController)
        }

        composable(route = Graph.CLIENT) {
            ClientHomeScreen()
        }
        composable(route = Graph.DRIVER) {
            DriverHomeScreen()
        }
    }
}