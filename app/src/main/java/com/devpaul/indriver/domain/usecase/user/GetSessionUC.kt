package com.devpaul.indriver.domain.usecase.user

import com.devpaul.indriver.domain.repository.AuthRepository

class GetSessionUC(private val authRepository: AuthRepository) {

    operator fun invoke() = authRepository.getSession()

}