package com.devpaul.indriver.domain.usecase.location

data class LocationUseCases(
    val getLocationUpdateUC: GetLocationUpdateUC,
    val getPlaceDetailsUC: GetPlaceDetailsUC,
    val getPlacePredictionsUC: GetPlacePredictionsUC,
    val getPlaceFromLatLngUC: GetPlaceFromLatLngUC,
)