package com.devpaul.indriver.data.datasource.remote

import com.devpaul.indriver.domain.model.req.LoginRequest
import com.devpaul.indriver.domain.model.req.RegisterRequest
import com.devpaul.indriver.domain.model.res.RegisterResponse
import com.devpaul.indriver.domain.model.res.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

}