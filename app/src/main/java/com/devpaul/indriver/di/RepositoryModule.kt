package com.devpaul.indriver.di

import com.devpaul.indriver.data.local.datastore.LocalDataStore
import com.devpaul.indriver.data.remote.datasource.remote.service.AuthService
import com.devpaul.indriver.data.remote.datasource.remote.service.UserService
import com.devpaul.indriver.data.repository.AuthRepositoryImpl
import com.devpaul.indriver.data.repository.UserRepositoryImpl
import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(authService: AuthService, localDataStore: LocalDataStore): AuthRepository = AuthRepositoryImpl(authService = authService, localDataStore = localDataStore)

    @Provides
    fun provideUserRepository(userService: UserService) : UserRepository = UserRepositoryImpl(userService = userService)
}