package com.devpaul.indriver.presentation.navigation.graph.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.devpaul.indriver.presentation.navigation.Graph
import com.devpaul.indriver.presentation.navigation.screen.profile.ProfileScreen
import com.devpaul.indriver.presentation.screens.profile.info.ProfileInfoScreen
import com.devpaul.indriver.presentation.screens.profile.update.ProfileUpdateScreen

fun NavGraphBuilder.profileNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.PROFILE,
        startDestination = ProfileScreen.ProfileInfo.route
    ) {
        composable(ProfileScreen.ProfileInfo.route) {
            ProfileInfoScreen(navHostController = navHostController)
        }

        composable(
            route = ProfileScreen.ProfileUpdate.route,
            arguments = listOf(navArgument(name = "user") {
                type = NavType.StringType
            }
            )
        ) {
            it.arguments?.getString("user")?.let { user ->
                ProfileUpdateScreen(navHostController = navHostController, userParam = user)
            }
        }
    }
}