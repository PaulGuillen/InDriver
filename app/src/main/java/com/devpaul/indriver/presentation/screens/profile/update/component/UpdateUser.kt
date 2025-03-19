package com.devpaul.indriver.presentation.screens.profile.update.component

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.indriver.domain.util.Resource
import com.devpaul.indriver.presentation.screens.profile.update.ProfileUpdateViewModel

@Composable
fun UpdateUser(navHostController: NavHostController, vm: ProfileUpdateViewModel = hiltViewModel()) {
    val context = LocalContext.current

    when (val response = vm.updateResponse) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            vm.updateUserSession(response.data)
           Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show()
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