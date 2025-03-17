package com.devpaul.indriver.domain.repository

import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.util.Resource
import java.io.File

interface UserRepository {

    suspend fun update(id: String, user: User, file: File?): Resource<User>
}