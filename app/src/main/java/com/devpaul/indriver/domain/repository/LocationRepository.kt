package com.devpaul.indriver.domain.repository

import com.devpaul.indriver.domain.model.res.PlacePrediction
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place

interface LocationRepository {

    fun getLocationUpdates(callback: (position: LatLng) -> Unit)

    suspend fun getPlacePredictions(query: String): List<PlacePrediction>

    suspend fun getPlaceDetails(placeId: String): Place

}