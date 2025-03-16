package com.devpaul.indriver.di

import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.usecase.AuthUseCase
import com.devpaul.indriver.domain.usecase.GetSessionUC
import com.devpaul.indriver.domain.usecase.LogOutUC
import com.devpaul.indriver.domain.usecase.LoginUC
import com.devpaul.indriver.domain.usecase.RegisterUC
import com.devpaul.indriver.domain.usecase.SaveSessionUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideAuthCase(authRepository: AuthRepository) = AuthUseCase(
        login = LoginUC(repository = authRepository),
        register = RegisterUC(repository = authRepository),
        saveSession = SaveSessionUC(authRepository = authRepository),
        getSession = GetSessionUC(authRepository = authRepository),
        logOutUC = LogOutUC(repository = authRepository),
    )
}