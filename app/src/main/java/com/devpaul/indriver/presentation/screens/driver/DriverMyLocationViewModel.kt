package com.devpaul.indriver.presentation.screens.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.usecase.location.LocationUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverMyLocationViewModel  @Inject constructor(
    private val locationUseCases: LocationUseCases,
) : ViewModel() {

    private val _location = MutableStateFlow<LatLng?>(null)
    val location: StateFlow<LatLng?> = _location

    fun startLocationUpdates() = viewModelScope.launch {
        locationUseCases.getLocationUpdateUC { position ->
            _location.value = position
        }
    }


}