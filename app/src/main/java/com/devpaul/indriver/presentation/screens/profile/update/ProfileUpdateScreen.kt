package com.devpaul.indriver.presentation.screens.profile.update

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import timber.log.Timber

@Composable
fun ProfileUpdateScreen(navHostController: NavHostController, userParam: String) {

    Timber.d("UserParams: $userParam")

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        Text(
            text = "Profile Update Screen", modifier = Modifier.padding(paddingValues)
        )
    }
}