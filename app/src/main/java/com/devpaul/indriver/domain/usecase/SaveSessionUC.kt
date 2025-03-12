package com.devpaul.indriver.domain.usecase

import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.repository.AuthRepository

class SaveSessionUC(private val authRepository: AuthRepository) {

    suspend operator fun invoke(loginResponse: LoginResponse) {
        authRepository.saveSession(loginResponse)
    }

}