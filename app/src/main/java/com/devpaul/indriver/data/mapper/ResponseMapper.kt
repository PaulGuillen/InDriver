package com.devpaul.indriver.data.mapper

import com.devpaul.indriver.domain.model.res.LoginResponse
import com.devpaul.indriver.domain.model.res.RegisterResponse
import com.devpaul.indriver.domain.model.res.RegisterRole
import com.devpaul.indriver.domain.model.res.RegisterUser
import com.devpaul.indriver.domain.model.res.Role
import com.devpaul.indriver.domain.model.res.User

class ResponseMapper {

    fun mapToLoginResponse(registerResponse: RegisterResponse): LoginResponse {
        return LoginResponse(
            user = mapRegisterUserToUser(registerResponse.user),
            token = registerResponse.token
        )
    }

    private fun mapRegisterUserToUser(registerUser: RegisterUser): User {
        return User(
            id = registerUser.id,
            name = registerUser.name,
            lastname = registerUser.lastname,
            email = registerUser.email,
            notificationToken = registerUser.notificationToken,
            phone = registerUser.phone,
            image = registerUser.image,
            createdAt = registerUser.createdAt,
            updatedAt = registerUser.updatedAt,
            roles = registerUser.roles.map { mapRegisterRoleToRole(it) }
        )
    }

    private fun mapRegisterRoleToRole(registerRole: RegisterRole): Role {
        return Role(
            id = registerRole.id,
            name = registerRole.name,
            image = registerRole.image,
            route = registerRole.route,
            createdAt = registerRole.createdAt,
            updatedAt = registerRole.updatedAt
        )
    }

}
