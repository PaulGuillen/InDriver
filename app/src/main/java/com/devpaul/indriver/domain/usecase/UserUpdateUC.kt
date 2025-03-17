package com.devpaul.indriver.domain.usecase

import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.domain.repository.UserRepository
import com.devpaul.indriver.domain.util.Resource
import java.io.File

class UserUpdateUC(private val repository: UserRepository) {

    suspend operator fun invoke(id: String, user: User, file: File?): Resource<User> {
        return repository.update(id = id, user = user, file = file)
    }
}