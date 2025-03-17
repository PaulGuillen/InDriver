package com.devpaul.indriver.data.remote.datasource.remote.service

import com.devpaul.indriver.domain.model.res.User
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {

    @PUT("users/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Body user: User,
    ): Response<User>
}