package com.devpaul.indriver.data.repository

import com.devpaul.indriver.data.datasource.remote.UserService
import com.devpaul.indriver.data.util.HandleRequest
import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.repository.UserRepository
import com.devpaul.indriver.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserRepositoryImpl(private val userService: UserService) : UserRepository {

    override suspend fun update(id: String, user: User, file: File?):
            Resource<User> {
        if (file != null) {
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

            return HandleRequest.send(
                userService.updateWithImage(
                    file = fileFormData,
                    id = id,
                    name = nameData,
                    lastname = lastnameData,
                    phone = phoneData
                )
            )

        } else {
            return HandleRequest.send(userService.update(id = id, user = user))
        }
    }

}