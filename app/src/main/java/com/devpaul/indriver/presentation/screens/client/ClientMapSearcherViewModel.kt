package com.devpaul.indriver.presentation.screens.client

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.model.res.PlacePrediction
import com.devpaul.indriver.domain.usecase.location.LocationUseCases
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientMapSearcherViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
) : ViewModel() {

    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location

    private val _placePredictions = MutableStateFlow<List<PlacePrediction>>(emptyList())
    val placePredictions: StateFlow<List<PlacePrediction>> get() = _placePredictions

    private val _originPlace = MutableStateFlow<Place?>(null)
    val originPlace: StateFlow<Place?> get() = _originPlace

    private val _destinationPlace = MutableStateFlow<Place?>(null)
    val destinationPlace: StateFlow<Place?> get() = _destinationPlace

    var isInteractingWithMap by mutableStateOf(false)

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
}