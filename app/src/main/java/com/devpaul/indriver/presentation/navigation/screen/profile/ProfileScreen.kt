package com.devpaul.indriver.presentation.navigation.screen.profile

sealed class ProfileScreen(val route: String) {
    data object ProfileInfo : ProfileScreen("/profile/info")
    data object ProfileUpdate : ProfileScreen("/profile/update/{user}") {
        fun passUser(user: String): String {
            return "/profile/update/$user"
        }
    }
}