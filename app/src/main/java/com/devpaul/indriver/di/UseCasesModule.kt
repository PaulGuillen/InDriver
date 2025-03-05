package com.devpaul.indriver.di

import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.usecase.AuthUseCase
import com.devpaul.indriver.domain.usecase.LoginUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideAuthCase(authRepository: AuthRepository) = AuthUseCase(
        login = LoginUC(repository = authRepository)
    )
}