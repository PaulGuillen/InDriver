package com.devpaul.indriver.domain.usecase.auth

import com.devpaul.indriver.domain.model.req.LoginRequest
import com.devpaul.indriver.domain.repository.AuthRepository

class LoginUC(private val repository: AuthRepository) {

    suspend operator fun invoke(loginRequest: LoginRequest) = repository.login(loginRequest)

}