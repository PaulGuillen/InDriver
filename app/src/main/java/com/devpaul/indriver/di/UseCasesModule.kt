package com.devpaul.indriver.di

import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.repository.LocationRepository
import com.devpaul.indriver.domain.repository.UserRepository
import com.devpaul.indriver.domain.usecase.auth.AuthUseCases
import com.devpaul.indriver.domain.usecase.user.GetSessionUC
import com.devpaul.indriver.domain.usecase.auth.LogOutUC
import com.devpaul.indriver.domain.usecase.auth.LoginUC
import com.devpaul.indriver.domain.usecase.auth.RegisterUC
import com.devpaul.indriver.domain.usecase.location.GetLocationUpdateUC
import com.devpaul.indriver.domain.usecase.location.GetPlaceDetailsUC
import com.devpaul.indriver.domain.usecase.location.GetPlaceFromLatLngUC
import com.devpaul.indriver.domain.usecase.location.GetPlacePredictionsUC
import com.devpaul.indriver.domain.usecase.location.LocationUseCases
import com.devpaul.indriver.domain.usecase.user.SaveSessionUC
import com.devpaul.indriver.domain.usecase.user.UpdateSessionUC
import com.devpaul.indriver.domain.usecase.user.UserUpdateUC
import com.devpaul.indriver.domain.usecase.user.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideAuthCase(authRepository: AuthRepository) = AuthUseCases(
        login = LoginUC(repository = authRepository),
        register = RegisterUC(repository = authRepository),
        saveSession = SaveSessionUC(authRepository = authRepository),
        getSession = GetSessionUC(authRepository = authRepository),
        logOutUC = LogOutUC(repository = authRepository),
        updateSessionUC = UpdateSessionUC(repository = authRepository)
    )

    @Provides
    fun provideUserCase(userRepository: UserRepository) = UserUseCases(
        update = UserUpdateUC(repository = userRepository),
    )

    @Provides
    fun provideLocationCase(locationRepository: LocationRepository) = LocationUseCases(
        getLocationUpdateUC = GetLocationUpdateUC(locationRepository = locationRepository),
        getPlaceDetailsUC = GetPlaceDetailsUC(locationRepository = locationRepository),
        getPlacePredictionsUC = GetPlacePredictionsUC(locationRepository = locationRepository),
        getPlaceFromLatLngUC = GetPlaceFromLatLngUC(locationRepository = locationRepository),
    )
}