package com.devpaul.indriver.presentation.screens.profile.info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCases : AuthUseCase
): ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    init{
        getSessionData()
    }

    private fun getSessionData() = viewModelScope.launch {
        authUseCases.getSession().collect() { data ->
            user = data.user
        }
    }
}