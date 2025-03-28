package com.devpaul.indriver.data.datasource.remote

import com.devpaul.indriver.domain.model.res.DirectionsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapService {

    @GET("maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String,
    ) : DirectionsResponse
}

//Do a mock
//https://maps.googleapis.com/maps/api/directions/json?origin=4.7149833,-74.072092&destination=4.7149833,-74.072092&key=