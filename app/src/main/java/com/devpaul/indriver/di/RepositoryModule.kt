package com.devpaul.indriver.di

import com.devpaul.indriver.data.datasource.remote.service.AuthService
import com.devpaul.indriver.data.repository.AuthRepositoryImpl
import com.devpaul.indriver.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(authService: AuthService): AuthRepository = AuthRepositoryImpl(authService)
}