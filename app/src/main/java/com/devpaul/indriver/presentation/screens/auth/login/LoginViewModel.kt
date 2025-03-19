package com.devpaul.indriver.presentation.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.model.req.LoginRequest
import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.usecase.auth.AuthUseCases
import com.devpaul.indriver.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    var errorMessage by mutableStateOf<String?>(null)

    var loginResponse by mutableStateOf<Resource<LoginResponse>?>(null)
        private set

    init {
        getSession()
    }

    fun onEmailChanged(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }

    fun onLoginClicked() = viewModelScope.launch {
        if (isValidForm()) {

            val request = LoginRequest(
                email = state.email,
                password = state.password
            )
            loginResponse = Resource.Loading
            val result = authUseCases.login(request)
            loginResponse = result

            Timber.d("Email: ${state.email}, Password: ${state.password}")
        }
    }

    fun saveSession(loginResponse: LoginResponse) = viewModelScope.launch {
        authUseCases.saveSession(loginResponse)
    }

    private fun getSession() = viewModelScope.launch {
        authUseCases.getSession().collect() { data ->
            if(!data.token.isNullOrBlank()) {
                loginResponse = Resource.Success(data)
            }
            Timber.d("Data: $data")
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