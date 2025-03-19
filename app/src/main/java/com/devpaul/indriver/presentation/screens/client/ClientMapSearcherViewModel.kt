package com.devpaul.indriver.presentation.screens.client

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devpaul.indriver.domain.usecase.location.LocationUseCases
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientMapSearcherViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
) : ViewModel() {

    val location = mutableStateOf<LatLng?>(null)

    fun startLocationUpdates() = viewModelScope.launch {
        locationUseCases.getLocationUpdateUC { position ->
            location.value = position
        }
    }
}