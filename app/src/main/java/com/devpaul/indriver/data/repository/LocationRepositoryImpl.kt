package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.datasource.location.LocationDataSource
import com.devpaul.indriver.domain.model.res.PlacePrediction
import com.devpaul.indriver.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationRepositoryImpl(
    private val locationDataSource: LocationDataSource,
    private val placesClient: PlacesClient,
) :
    LocationRepository {

    override fun getLocationUpdates(callback: (position: LatLng) -> Unit) {
        locationDataSource.getLocationUpdates(callback)
    }

    override suspend fun getPlacePredictions(query: String): List<PlacePrediction> {
        val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
        return suspendCancellableCoroutine { continuation ->
            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    val predictions = response.autocompletePredictions.map {
                        PlacePrediction(
                            placeId = it.placeId,
                            fullText = it.getFullText(null).toString(),
                        )
                    }
                    continuation.resumeWith(Result.success(predictions))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun getPlaceDetails(placeId: String): Place {
        val request = FetchPlaceRequest.builder(
            placeId,
            listOf(Place.Field.ID, Place.Field.LAT_LNG),
        ).build()

        return suspendCancellableCoroutine { continuation ->
            placesClient.fetchPlace(request)
                .addOnSuccessListener { response ->
                    continuation.resumeWith(Result.success(response.place))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }
}