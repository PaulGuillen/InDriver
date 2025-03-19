package com.devpaul.indriver.domain.usecase.auth

import com.devpaul.indriver.domain.model.req.RegisterRequest
import com.devpaul.indriver.domain.repository.AuthRepository

class RegisterUC(private val repository: AuthRepository) {

    suspend operator fun invoke(registerRequest: RegisterRequest) = repository.register(registerRequest)

}