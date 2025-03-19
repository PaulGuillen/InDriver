package com.devpaul.indriver.domain.repository

import com.devpaul.indriver.domain.model.req.LoginRequest
import com.devpaul.indriver.domain.model.req.RegisterRequest
import com.devpaul.indriver.domain.model.res.RegisterResponse
import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse>

    suspend fun register(registerRequest: RegisterRequest): Resource<RegisterResponse>

    suspend fun saveSession(loginResponse: LoginResponse)

    suspend fun updateSession(user: User)

    suspend fun logOut()

    fun getSession(): Flow<LoginResponse>
}