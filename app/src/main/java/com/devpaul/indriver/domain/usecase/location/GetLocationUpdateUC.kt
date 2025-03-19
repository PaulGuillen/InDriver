package com.devpaul.indriver.domain.usecase.location

import com.devpaul.indriver.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng

class GetLocationUpdateUC(private val locationRepository: LocationRepository) {

    operator fun invoke(callback: (position: LatLng) -> Unit) =
        locationRepository.getLocationUpdates(callback)
}