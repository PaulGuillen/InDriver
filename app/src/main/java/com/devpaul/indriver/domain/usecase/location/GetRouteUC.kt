package com.devpaul.indriver.domain.usecase.location

import com.devpaul.indriver.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng

data class GetRouteUC(
    private val locationRepository: LocationRepository,
) {

    suspend operator fun invoke(
        origin: LatLng,
        destination: LatLng,
    ): List<LatLng>? = locationRepository.getRoute(origin, destination)
}