package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.datasource.location.LocationDataSource
import com.devpaul.indriver.domain.repository.LocationRepository
import com.google.android.gms.maps.model.LatLng

class LocationRepositoryImpl(private val locationDataSource: LocationDataSource) :
    LocationRepository {

    override fun getLocationUpdates(callback: (position: LatLng) -> Unit) {
       locationDataSource.getLocationUpdates(callback)
    }
}