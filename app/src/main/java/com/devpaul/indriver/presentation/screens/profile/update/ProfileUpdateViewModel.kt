package com.devpaul.indriver.presentation.screens.profile.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.data.mapper.toUser
import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.usecase.UserUseCase
import com.devpaul.indriver.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(ProfileUpdateState())
        private set

    val data = savedStateHandle.get<String>("user")
    val user = User.fromJson(data)

    var updateResponse by mutableStateOf<Resource<User>?>(null)
        private set

    init {
        state = state.copy(
            name = user?.name ?: "",
            lastname = user?.lastname ?: "",
            phone = user?.phone ?: "",
            image = user?.image,
        )
    }

    fun update() = viewModelScope.launch {
        updateResponse = Resource.Loading
        val result = userUseCase.update(
            id = user?.id.toString(),
            user = state.toUser(),
            file = null,
        )
        updateResponse = result
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