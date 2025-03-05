package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.datasource.remote.service.AuthService
import com.devpaul.indriver.domain.model.res.UserResponse
import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.util.Resource
import timber.log.Timber

class AuthRepositoryImpl(
    private val authService: AuthService,
) : AuthRepository {

    override suspend fun login(email: String, password: String):
            Resource<UserResponse> {
        return try {
            val result = authService.login(email, password)
            if (result.isSuccessful){
                result.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(500, "Error: ${result.message()}")
            } else {
                Resource.Error(result.code(), "Error: ${result.message()}")
            }
        } catch (e: Exception){
            Timber.d("Error: ${e.message}")
            Resource.Error(500, "Error: ${e.message}")
        }
    }

}