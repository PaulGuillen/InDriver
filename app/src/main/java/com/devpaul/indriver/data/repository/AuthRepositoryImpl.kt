package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.local.datastore.LocalDataStore
import com.devpaul.indriver.data.remote.datasource.remote.service.AuthService
import com.devpaul.indriver.domain.model.req.LoginRequest
import com.devpaul.indriver.domain.model.req.RegisterRequest
import com.devpaul.indriver.domain.model.res.RegisterResponse
import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.util.ErrorHelper
import com.devpaul.indriver.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val localDataStore: LocalDataStore,
) : AuthRepository {

    override suspend fun login(loginRequest : LoginRequest):
            Resource<LoginResponse> {
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

    override suspend fun register(registerRequest : RegisterRequest):
            Resource<RegisterResponse> {
        return try {
            val result = authService.register(registerRequest)
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

    override suspend fun saveSession(loginResponse: LoginResponse) = localDataStore.save(loginResponse)

    override suspend fun logOut() = localDataStore.delete()

    override fun getSession(): Flow<LoginResponse> = localDataStore.getData()

}