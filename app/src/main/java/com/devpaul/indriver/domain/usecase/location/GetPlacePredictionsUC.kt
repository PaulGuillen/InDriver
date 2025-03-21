package com.devpaul.indriver.domain.usecase.location

import com.devpaul.indriver.domain.repository.LocationRepository

class GetPlacePredictionsUC(private val locationRepository: LocationRepository) {

    suspend operator fun invoke(query: String) = locationRepository.getPlacePredictions(query)
}