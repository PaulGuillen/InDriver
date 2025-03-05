package com.devpaul.indriver.domain.repository

import com.devpaul.indriver.domain.model.res.UserResponse
import com.devpaul.indriver.domain.util.Resource

interface AuthRepository {

    suspend fun login(email: String, password: String): Resource<UserResponse>
}