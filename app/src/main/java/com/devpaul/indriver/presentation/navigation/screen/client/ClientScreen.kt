package com.devpaul.indriver.presentation.navigation.screen.client

sealed class ClientScreen(val route : String) {
    data object ClientHome : ClientScreen("/client/home")
}