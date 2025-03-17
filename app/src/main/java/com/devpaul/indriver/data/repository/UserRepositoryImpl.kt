package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.remote.datasource.remote.service.UserService
import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.repository.UserRepository
import com.devpaul.indriver.domain.util.ErrorHelper
import com.devpaul.indriver.domain.util.Resource
import timber.log.Timber
import java.io.File

class UserRepositoryImpl(private val userService: UserService) : UserRepository {

    override suspend fun update(id: String, user: User, file: File?):
            Resource<User> {
        return try {
            val result = userService.update(id = id, user = user)
            if (result.isSuccessful) {
                result.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(500, "Error: ${result.message()}")
            } else {
                val errorResponse = ErrorHelper.handleError(result.errorBody())
                errorResponse?.let {
                    Resource.Error(errorResponse.statusCode, errorResponse.message)
                } ?: Resource.Error(result.code(), "Error: ${result.message()}")
            }
        } catch (e: Exception) {
            Timber.d("Error: ${e.message}")
            Resource.Error(500, "Error: ${e.message}")
        }
    }

}