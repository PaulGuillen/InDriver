package com.devpaul.indriver.presentation.screens.auth.register

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.devpaul.indriver.presentation.screens.auth.register.components.Register
import com.devpaul.indriver.presentation.screens.auth.register.components.RegisterContent

@Composable
fun RegisterScreen(navHostController: NavHostController) {
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        RegisterContent(navHostController = navHostController, paddingValues = paddingValues)
    }
    Register()
}