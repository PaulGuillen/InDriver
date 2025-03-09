package com.devpaul.indriver.presentation.screens.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.model.req.RegisterRequest
import com.devpaul.indriver.domain.model.res.RegisterResponse
import com.devpaul.indriver.domain.usecase.AuthUseCase
import com.devpaul.indriver.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    var errorMessage by mutableStateOf<String?>(null)

    var registerResponse by mutableStateOf<Resource<RegisterResponse>?>(null)
        private set

    fun onNameChanged(name: String) {
        state = state.copy(name = name)
    }

    fun onLastNameChanged(lastName: String) {
        state = state.copy(lastName = lastName)
    }

    fun onPhoneChanged(phone: String) {
        state = state.copy(phone = phone)
    }

    fun onEmailChanged(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        state = state.copy(confirmPassword = confirmPassword)
    }

    fun onRegisterClicked() = viewModelScope.launch {
        if (isValidForm()) {

            val request = RegisterRequest(
                name = state.name,
                lastname = state.lastName,
                phone = state.phone,
                email = state.email,
                password = state.password
            )
            registerResponse = Resource.Loading
            val result = authUseCase.register(request)
            registerResponse = result
        }
    }

    private fun isValidForm(): Boolean {
        errorMessage = null

        if (state.name.isEmpty()) {
            errorMessage = "Nombre requerido"
            return false
        }

        if (state.lastName.isEmpty()) {
            errorMessage = "Apellido requerido"
            return false
        }

        if (state.phone.isEmpty()) {
            errorMessage = "Teléfono requerido"
            return false
        }

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

        if (state.confirmPassword.isEmpty()) {
            errorMessage = "Confirmar contraseña requerida"
            return false
        }

        if (state.password != state.confirmPassword) {
            errorMessage = "Las contraseñas no coinciden"
            return false
        }

        return true
    }

}