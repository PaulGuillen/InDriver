package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.datasource.remote.service.AuthService
import com.devpaul.indriver.domain.model.req.LoginRequest
import com.devpaul.indriver.domain.model.res.UserResponse
import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.util.ErrorHelper
import com.devpaul.indriver.domain.util.Resource
import timber.log.Timber

class AuthRepositoryImpl(
    private val authService: AuthService,
) : AuthRepository {

    override suspend fun login(loginRequest : LoginRequest):
            Resource<UserResponse> {
        return try {
            val result = authService.login(loginRequest)
            if (result.isSuccessful){
                result.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(500, "Error: ${result.message()}")
            } else {
                val errorResponse = ErrorHelper.handleError(result.errorBody())
                errorResponse?.let {
                    Resource.Error(errorResponse.statusCode, errorResponse.message)
                } ?: Resource.Error(result.code(), "Error: ${result.message()}")
            }
        } catch (e: Exception){
            Timber.d("Error: ${e.message}")
            Resource.Error(500, "Error: ${e.message}")
        }
    }

}