package com.devpaul.indriver.presentation.screens.profile.info

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ProfileInfoScreen(navHostController: NavHostController) {

    Scaffold { paddingValues ->
        Text(
            modifier = Modifier.padding(paddingValues),
            text = "Profile Screen"
        )
    }
}