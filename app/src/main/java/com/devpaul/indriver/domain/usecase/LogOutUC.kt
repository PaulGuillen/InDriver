package com.devpaul.indriver.domain.usecase

import com.devpaul.indriver.domain.repository.AuthRepository

class LogOutUC(private val repository: AuthRepository) {

    suspend operator fun invoke() {
        repository.logOut()
    }
}