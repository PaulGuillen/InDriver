package com.devpaul.indriver.domain.usecase.location

import com.devpaul.indriver.domain.repository.LocationRepository

class GetPlaceDetailsUC(private val locationRepository: LocationRepository) {

    suspend operator fun invoke(placeId: String) = locationRepository.getPlaceDetails(placeId)
}