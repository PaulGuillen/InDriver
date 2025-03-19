package com.devpaul.indriver.presentation.screens.client.mapSearcher

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.devpaul.indriver.presentation.screens.client.mapSearcher.components.ClientMapSearcherContent

@Composable
fun MapSearcherScreen(navHostController: NavHostController) {

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        ClientMapSearcherContent(
            navHostController = navHostController,
            paddingValues = paddingValues,
        )
    }
}