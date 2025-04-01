package com.devpaul.indriver.presentation.screens.roles

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.devpaul.indriver.presentation.screens.roles.components.RolesContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RolesScreen(
    navHostController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Selecciona Rol"
                    )
                }
            )
        }
    ) { paddingValues ->
        RolesContent(paddingValues = paddingValues, navHostController = navHostController)
    }
}