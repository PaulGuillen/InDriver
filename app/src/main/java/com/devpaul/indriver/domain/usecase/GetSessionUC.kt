package com.devpaul.indriver.domain.usecase

import com.devpaul.indriver.domain.repository.AuthRepository

class GetSessionUC(private val authRepository: AuthRepository) {

    operator fun invoke() = authRepository.getSession()

}