package com.devpaul.indriver.presentation.screens.auth.login.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.indriver.domain.util.Resource
import com.devpaul.indriver.presentation.navigation.Graph
import com.devpaul.indriver.presentation.screens.auth.login.LoginViewModel

@Composable
fun Login(navHostController: NavHostController, vm: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current

    when (val response = vm.loginResponse) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            LaunchedEffect(Unit) {
                vm.saveSession(response.data)
                navHostController.navigate(route = Graph.CLIENT) {
                    popUpTo(Graph.AUTH) {
                        inclusive = true
                    }
                }
            }
        }

        is Resource.Error -> {
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        }

        else -> {
            if (response != null) {
                Toast.makeText(context, "Error desconocido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}