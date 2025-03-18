package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.remote.datasource.remote.service.UserService
import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.repository.UserRepository
import com.devpaul.indriver.domain.util.ErrorHelper
import com.devpaul.indriver.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.File

class UserRepositoryImpl(private val userService: UserService) : UserRepository {

    override suspend fun update(id: String, user: User, file: File?):
            Resource<User> {
        if (file != null) {
            return try {
                val connection = withContext(Dispatchers.IO) {
                    file.toURI().toURL().openConnection()
                }
                val mimeType = connection.contentType
                val contentType = "text/plain"
                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                val fileFormData = MultipartBody.Part.createFormData("file", file.name, requestFile)
                val nameData = user.name.toRequestBody(contentType.toMediaTypeOrNull())
                val lastnameData = user.lastname.toRequestBody(contentType.toMediaTypeOrNull())
                val phoneData = user.phone.toRequestBody(contentType.toMediaTypeOrNull())

                val result = userService.updateWithImage(fileFormData, id, nameData, lastnameData, phoneData)
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

        } else {
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

}