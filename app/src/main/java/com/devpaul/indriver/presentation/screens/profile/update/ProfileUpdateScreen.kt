package com.devpaul.indriver.presentation.screens.profile.update

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.devpaul.indriver.presentation.screens.profile.update.component.ProfileUpdateContent
import com.devpaul.indriver.presentation.screens.profile.update.component.UpdateUser

@Composable
fun ProfileUpdateScreen(navHostController: NavHostController, userParam: String) {

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
            ProfileUpdateContent(
                navHostController = navHostController,
                paddingValues = paddingValues,
            )
    }
    UpdateUser(navHostController = navHostController)
}