package com.devpaul.indriver.presentation.screens.auth.register.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.devpaul.indriver.domain.util.Resource
import com.devpaul.indriver.presentation.screens.auth.register.RegisterViewModel

@Composable
fun Register(vm : RegisterViewModel = hiltViewModel()) {
    val context = LocalContext.current

    when (val response = vm.registerResponse) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is Resource.Success -> {
            Toast.makeText(context, "Registro correcto", Toast.LENGTH_SHORT).show()
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