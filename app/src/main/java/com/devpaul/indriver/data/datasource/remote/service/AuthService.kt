package com.devpaul.indriver.data.datasource.remote.service

import com.devpaul.indriver.domain.model.res.UserResponse
import retrofit2.Response
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun login(
        email: String,
        password: String
    ): Response<UserResponse>

}