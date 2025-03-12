package com.devpaul.indriver.presentation.navigation.graph.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.devpaul.indriver.presentation.navigation.Graph
import com.devpaul.indriver.presentation.navigation.graph.client.clientNavGraph
import com.devpaul.indriver.presentation.navigation.screen.auth.AuthScreen
import com.devpaul.indriver.presentation.screens.auth.login.LoginScreen
import com.devpaul.indriver.presentation.screens.auth.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Login.routes
    ) {
        composable(AuthScreen.Login.routes) {
            LoginScreen(navHostController = navHostController)
        }
        composable(AuthScreen.Register.routes) {
            RegisterScreen(navHostController = navHostController)
        }
        clientNavGraph(navHostController)
    }
}