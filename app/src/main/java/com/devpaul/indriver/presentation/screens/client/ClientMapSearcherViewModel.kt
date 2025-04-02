package com.devpaul.indriver.presentation.screens.client

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.model.res.PlacePrediction
import com.devpaul.indriver.domain.usecase.location.LocationUseCases
import com.devpaul.indriver.domain.usecase.socket.SocketUseCases
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClientMapSearcherViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
    private val socketUseCases: SocketUseCases,
) : ViewModel() {

    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location

    private val _placePredictions = MutableStateFlow<List<PlacePrediction>>(emptyList())
    val placePredictions: StateFlow<List<PlacePrediction>> get() = _placePredictions

    private val _originPlace = MutableStateFlow<Place?>(null)
    val originPlace: StateFlow<Place?> get() = _originPlace

    private val _destinationPlace = MutableStateFlow<Place?>(null)
    val destinationPlace: StateFlow<Place?> get() = _destinationPlace

    private val _route = MutableStateFlow<List<LatLng>?>(null)
    val route: StateFlow<List<LatLng>?> get() = _route

    var isInteractingWithMap by mutableStateOf(false)

    private val _driverMarkers = MutableStateFlow<Map<String, LatLng>>(emptyMap())
    val driverMarkers: StateFlow<Map<String, LatLng>> get() = _driverMarkers

    fun startLocationUpdates() = viewModelScope.launch {
        locationUseCases.getLocationUpdateUC { position ->
            _location.value = position
        }
    }

    fun getPlacePredictions(query: String) = viewModelScope.launch {
        val predictions = locationUseCases.getPlacePredictionsUC(query)
        _placePredictions.value = predictions
    }

    fun getPlaceDetails(
        placeId: String,
        isOrigin: Boolean,
        onPlaceSelected: (place: Place) -> Unit,
    ) = viewModelScope.launch {
        val place = locationUseCases.getPlaceDetailsUC(placeId)
        if (isOrigin) {
            _originPlace.value = place
        } else {
            _destinationPlace.value = place
        }
        onPlaceSelected(place)
    }

    fun getPlaceFromLatLng(latLng: LatLng) =
        viewModelScope.launch {
            val place = locationUseCases.getPlaceFromLatLngUC(latLng)
            _originPlace.value = place
        }

    fun getRoute() = viewModelScope.launch {
        if (_originPlace.value == null || _destinationPlace.value == null) {
            _route.value = locationUseCases.getRouteUC(
                _originPlace.value!!.latLng,
                _destinationPlace.value!!.latLng
            )
        }
    }

    fun connectSocketIO() {
        socketUseCases.connectSocketUC()
        Timber.d("ClientMapSearcher ViewModel: connectSocketIO")
    }

    fun disconnectSocketIO() {
        socketUseCases.disconnectSocketUC()
    }

    fun listenerDriversPositionSocket() {
        socketUseCases.setEventListenerSocketUC("new_driver_position", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            val idSocket = data.getString("id_socket")
            val id = data.getString("id")
            val lat = data.getDouble("lat")
            val lng = data.getDouble("lng")
            val newPosition = LatLng(lat, lng)
            Timber.d("ClientMapSearcher ViewModel: $data")
            viewModelScope.launch {
                _driverMarkers.value = _driverMarkers.value.toMutableMap().apply {
                    put(idSocket, newPosition)
                }
            }
        })
    }

    fun listenerDriversDisconnectedSocket() {
        socketUseCases.setEventListenerSocketUC("driver_disconnected", Emitter.Listener { args ->
            val data = args[0] as JSONObject
            val idSocket = data.getString("id_socket")
            viewModelScope.launch {
                _driverMarkers.value = _driverMarkers.value.toMutableMap().apply {
                    remove(idSocket)
                }
            }
        })
    }
}