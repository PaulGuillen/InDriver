package com.devpaul.indriver.di

import android.location.Geocoder
import com.devpaul.indriver.data.datasource.local.datastore.LocalDataStore
import com.devpaul.indriver.data.datasource.location.LocationDataSource
import com.devpaul.indriver.data.datasource.remote.AuthService
import com.devpaul.indriver.data.datasource.remote.GoogleMapService
import com.devpaul.indriver.data.datasource.remote.UserService
import com.devpaul.indriver.data.repository.AuthRepositoryImpl
import com.devpaul.indriver.data.repository.LocationRepositoryImpl
import com.devpaul.indriver.data.repository.SocketRepositoryImpl
import com.devpaul.indriver.data.repository.UserRepositoryImpl
import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.repository.LocationRepository
import com.devpaul.indriver.domain.repository.SocketRepository
import com.devpaul.indriver.domain.repository.UserRepository
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.Socket

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(
        authService: AuthService,
        localDataStore: LocalDataStore,
    ): AuthRepository =
        AuthRepositoryImpl(authService = authService, localDataStore = localDataStore)

    @Provides
    fun provideUserRepository(userService: UserService): UserRepository =
        UserRepositoryImpl(userService = userService)

    @Provides
    fun provideLocationRepository(
        locationDataSource: LocationDataSource,
        placesClient: PlacesClient,
        geocoder: Geocoder,
        googleMapsService: GoogleMapService
    ): LocationRepository =
        LocationRepositoryImpl(
            locationDataSource = locationDataSource,
            placesClient = placesClient,
            geoCoder = geocoder,
            googleMapsService = googleMapsService
        )

    @Provides
    fun provideSocket(socket: Socket): SocketRepository =
        SocketRepositoryImpl(socket = socket)
}