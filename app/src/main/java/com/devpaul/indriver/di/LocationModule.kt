package com.devpaul.indriver.di

import android.content.Context
import android.location.Geocoder
import com.devpaul.indriver.data.datasource.location.LocationDataSource
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationDataSource(@ApplicationContext context: Context): LocationDataSource =
        LocationDataSource(context)

    @Provides
    @Singleton
    fun provideGeoCoder(@ApplicationContext context: Context): Geocoder =
        Geocoder(context, Locale.getDefault())

    @Provides
    @Singleton
    fun providePlaceClient(@ApplicationContext context: Context): PlacesClient =
        Places.createClient(context)

}