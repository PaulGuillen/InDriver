package com.devpaul.indriver.data.repository

import android.location.Address
import android.location.Geocoder
import com.devpaul.indriver.data.datasource.location.LocationDataSource
import com.devpaul.indriver.domain.model.res.PlacePrediction
import com.devpaul.indriver.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okio.IOException
import timber.log.Timber
import kotlin.coroutines.resumeWithException

class LocationRepositoryImpl(
    private val locationDataSource: LocationDataSource,
    private val placesClient: PlacesClient,
    private val geoCoder: Geocoder,
) : LocationRepository {

    override fun getLocationUpdates(callback: (position: LatLng) -> Unit) {
        locationDataSource.getLocationUpdates(callback)
    }

    override suspend fun getPlacePredictions(query: String): List<PlacePrediction> {
        val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()
        return suspendCancellableCoroutine { continuation ->
            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    val predictions = response.autocompletePredictions.map { prediction ->
                        PlacePrediction(
                            placeId = prediction.placeId,
                            fullText = prediction.getFullText(null).toString()
                        )
                    }
                    continuation.resume(predictions) {}
                }.addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }
    }

    override suspend fun getPlaceDetails(placeId: String): Place {
        val request = FetchPlaceRequest.builder(
            placeId,
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)
        ).build()
        return suspendCancellableCoroutine { continuation ->
            placesClient.fetchPlace(request)
                .addOnSuccessListener { response ->
                    continuation.resume(response.place) {}
                }.addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }
        }
    }

    override suspend fun getPlaceFromLatLong(latLng: LatLng): Place? {
        return withContext(Dispatchers.IO) {
            try {
                val addresses: List<Address>? = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                if (addresses?.isNotEmpty() == true) {
                    val address = addresses[0]
                    Place.builder()
                        .setName(address.featureName ?: "Direccion desconocida")
                        .setAddress(address.getAddressLine(0) ?: "Sin direccion")
                        .setLatLng(LatLng(address.latitude, address.longitude))
                        .build()
                } else {
                    null
                }
            } catch (e: IOException) {
                Timber.d("LocationRepository getPlaceFromLatLong: ${e.message}")
                null
            }
        }
    }
}