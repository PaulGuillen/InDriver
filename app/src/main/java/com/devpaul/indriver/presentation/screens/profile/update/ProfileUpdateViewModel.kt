package com.devpaul.indriver.presentation.screens.profile.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.devpaul.indriver.domain.model.res.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(ProfileUpdateState())
        private set

    val data = savedStateHandle.get<String>("user")
    val user = User.fromJson(data)

    init {
        state = state.copy(
            name = user?.name ?: "",
            lastname = user?.lastname ?: "",
            phone = user?.phone ?: "",
            image = user?.image
        )
    }

    fun onNameChange(name: String) {
        state = state.copy(name = name)
    }

    fun onLastnameChange(lastname: String) {
        state = state.copy(lastname = lastname)
    }

    fun onPhoneChange(phone: String) {
        state = state.copy(phone = phone)
    }

}