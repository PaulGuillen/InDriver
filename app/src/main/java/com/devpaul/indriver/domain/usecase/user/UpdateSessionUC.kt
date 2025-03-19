package com.devpaul.indriver.domain.usecase.user

import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.repository.AuthRepository

class UpdateSessionUC(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User) = repository.updateSession(user)
}