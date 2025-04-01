package com.devpaul.indriver.presentation.screens.roles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.usecase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RolesViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
) : ViewModel() {

    var authReponse by mutableStateOf(LoginResponse())

    init {
        getSessionData()
    }

    fun getSessionData() = viewModelScope.launch {
        authUseCases.getSession().collect() { data ->
            if (!data.token.isNullOrBlank()) {
                authReponse = data
            }
        }
    }

}