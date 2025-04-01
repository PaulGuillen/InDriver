package com.devpaul.indriver.presentation.navigation.screen.roles

sealed class RolesScreen(val route: String) {
    data object Roles : RolesScreen("/roles")
}