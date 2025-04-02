package com.devpaul.indriver.presentation.screens.driver

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.usecase.auth.AuthUseCases
import com.devpaul.indriver.domain.usecase.location.LocationUseCases
import com.devpaul.indriver.domain.usecase.socket.SocketUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DriverMyLocationViewModel  @Inject constructor(
    private val locationUseCases: LocationUseCases,
    private val socketUseCases: SocketUseCases,
    private val socket : Socket,
    private val authUseCases: AuthUseCases,
) : ViewModel() {

    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location
    var isSocketConnected by mutableStateOf(false)
    var authResponse by mutableStateOf(LoginResponse())

    init {
        getSessionData()
        socket.on(Socket.EVENT_CONNECT) {
            Timber.d("Socket connected")
            isSocketConnected = true
        }
        socket.on(Socket.EVENT_DISCONNECT) {
            Timber.d("Socket disconnected")
            isSocketConnected = false
        }
    }


    fun getSessionData() = viewModelScope.launch {
        authUseCases.getSession().collect() { data ->
            if (!data.token.isNullOrBlank()) {
                authResponse = data
            }
        }
    }

    fun startLocationUpdates() = viewModelScope.launch {
        locationUseCases.getLocationUpdateUC { position ->
            _location.value = position
            emitPositionSocket()
        }
    }

    fun connectSocket(){
        socketUseCases.connectSocketUC()
    }

    fun disconnectSocket(){
        socketUseCases.disconnectSocketUC()
    }

    fun emitPositionSocket() {
        authResponse.let {
            val data = JSONObject().apply {
                put("id", it.user?.id)
                put("lat", _location.value?.latitude)
                put("lng", _location.value?.longitude)
            }
            socketUseCases.emitSocketUC("change_driver_position", data)
        }
    }

}