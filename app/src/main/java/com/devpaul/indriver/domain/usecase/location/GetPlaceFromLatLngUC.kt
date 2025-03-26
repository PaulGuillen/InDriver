package com.devpaul.indriver.domain.usecase.location

import com.devpaul.indriver.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng

class GetPlaceFromLatLngUC(private val locationRepository: LocationRepository) {

    suspend operator fun invoke(latLng: LatLng) = locationRepository.getPlaceFromLatLong(latLng)
}