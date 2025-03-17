package com.devpaul.indriver.data.mapper

import com.devpaul.indriver.domain.model.res.User
import com.devpaul.indriver.presentation.screens.profile.update.ProfileUpdateState

fun ProfileUpdateState.toUser(): User {
    return User(
        name = name,
        lastname = lastname,
        phone = phone,
        image = image
    )
}