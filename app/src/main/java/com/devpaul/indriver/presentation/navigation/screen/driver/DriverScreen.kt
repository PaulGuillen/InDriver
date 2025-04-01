package com.devpaul.indriver.presentation.navigation.screen.driver

sealed class DriverScreen(val route: String) {
    data object MapMyLocation : DriverScreen("/driver/map/my_location")
}