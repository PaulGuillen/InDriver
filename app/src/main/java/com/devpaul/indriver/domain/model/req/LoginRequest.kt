package com.devpaul.indriver.domain.model.req

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginRequest (
    @SerializedName("email")
    val email: String?= null,
    @SerializedName("password")
    val password: String ?= null,
) : Serializable