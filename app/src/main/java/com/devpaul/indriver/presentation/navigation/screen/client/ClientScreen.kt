package com.devpaul.indriver.presentation.navigation.screen.client

sealed class ClientScreen(val route : String) {
    data object ClientHome : ClientScreen("/client/home")
    data object ProfileInfo : ClientScreen("/client/profile/info")
    data object MapSearcher : ClientScreen("/client/map/searcher")
}