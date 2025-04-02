package com.devpaul.indriver.presentation.screens.driver

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.usecase.location.LocationUseCases
import com.devpaul.indriver.domain.usecase.socket.SocketUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DriverMyLocationViewModel  @Inject constructor(
    private val locationUseCases: LocationUseCases,
    private val socketUseCases: SocketUseCases,
    private val socket : Socket,
) : ViewModel() {

    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location
    var isSocketConnected by mutableStateOf(false)

    init {
        socket.on(Socket.EVENT_CONNECT) {
            Timber.d("Socket connected")
            isSocketConnected = true
        }
        socket.on(Socket.EVENT_DISCONNECT) {
            Timber.d("Socket disconnected")
            isSocketConnected = false
        }
    }

    fun startLocationUpdates() = viewModelScope.launch {
        locationUseCases.getLocationUpdateUC { position ->
            _location.value = position
        }
    }

    fun connectSocket(){
        socketUseCases.connectSocketUC()
    }

    fun disconnectSocket(){
        socketUseCases.disconnectSocketUC()
    }

}