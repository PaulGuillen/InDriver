package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.datasource.local.datastore.LocalDataStore
import com.devpaul.indriver.data.datasource.remote.AuthService
import com.devpaul.indriver.data.util.HandleRequest
import com.devpaul.indriver.domain.model.req.LoginRequest
import com.devpaul.indriver.domain.model.req.RegisterRequest
import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.model.res.RegisterResponse
import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.repository.AuthRepository
import com.devpaul.indriver.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val localDataStore: LocalDataStore,
) : AuthRepository {

    override suspend fun login(loginRequest: LoginRequest):
            Resource<LoginResponse> = HandleRequest.send(authService.login(loginRequest))

    override suspend fun register(registerRequest: RegisterRequest):
            Resource<RegisterResponse> = HandleRequest.send(authService.register(registerRequest))

    override suspend fun saveSession(loginResponse: LoginResponse) =
        localDataStore.save(loginResponse)

    override suspend fun updateSession(user: User) = localDataStore.update(user)

    override suspend fun logOut() = localDataStore.delete()

    override fun getSession(): Flow<LoginResponse> = localDataStore.getData()

}