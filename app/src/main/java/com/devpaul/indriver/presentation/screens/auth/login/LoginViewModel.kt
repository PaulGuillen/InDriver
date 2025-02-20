package com.devpaul.indriver.presentation.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    var errorMessage by mutableStateOf<String?>(null)

    fun onEmailChanged(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }

    fun onLoginClicked() {
        if (isValidForm()) {
            Timber.d("Email: ${state.email}, Password: ${state.password}")
        }
    }

    private fun isValidForm(): Boolean {
        errorMessage = null

        if (state.email.isEmpty()) {
            errorMessage = "Correo requerido"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            errorMessage = "Correo no valido"
            return false
        }

        if (state.password.isEmpty()) {
            errorMessage = "Contraseña requerida"
            return false
        }

        if (state.password.length < 6) {
            errorMessage = "Contraseña debe tener al menos 6 caracteres"
            return false
        }

        return true
    }
}