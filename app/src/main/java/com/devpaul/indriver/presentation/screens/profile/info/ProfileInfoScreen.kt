package com.devpaul.indriver.presentation.screens.profile.info

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.devpaul.indriver.presentation.screens.profile.info.components.ProfileInfoContent

@Composable
fun ProfileInfoScreen(navHostController: NavHostController) {

    Scaffold { paddingValues ->
        ProfileInfoContent(navHostController = navHostController, paddingValues = paddingValues)
    }
}