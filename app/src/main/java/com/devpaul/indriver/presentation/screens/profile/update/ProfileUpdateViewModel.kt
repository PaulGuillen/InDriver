package com.devpaul.indriver.presentation.screens.profile.update

import android.content.Context
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
import com.devpaul.indriver.presentation.util.ComposeFileProvider
import com.devpaul.indriver.presentation.util.ResultingActivityHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    var state by mutableStateOf(ProfileUpdateState())
        private set

    val data = savedStateHandle.get<String>("user")
    val user = User.fromJson(data)

    var updateResponse by mutableStateOf<Resource<User>?>(null)
        private set

    private var file: File? = null
    var resultingActivityHandler = ResultingActivityHandler()

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

    fun pickImage() = viewModelScope.launch {
        val result = resultingActivityHandler.getContent("image/*")
        if (result != null) {
            file = ComposeFileProvider.createFileFromUri(context, result)
            state = state.copy(image = result.toString())
        }
    }

    fun takePhoto() = viewModelScope.launch {
        val result = resultingActivityHandler.takePicturePreview()
        if (result != null) {
            state = state.copy(image = ComposeFileProvider.getPathFromBitmap(context, result))
            file = state.image?.let { File(it) }
        }
    }


}