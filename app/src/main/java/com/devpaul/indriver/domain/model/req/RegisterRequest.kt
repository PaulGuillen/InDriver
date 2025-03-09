package com.devpaul.indriver.domain.model.req

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterRequest (
    @SerializedName("name")
    val name: String?= null,
    @SerializedName("lastname")
    val lastname: String?= null,
    @SerializedName("email")
    val email: String?= null,
    @SerializedName("password")
    val password: String ?= null,
    @SerializedName("phone")
    val phone: String ?= null,
    @SerializedName("rolesIds")
    val rolesIds: List<String>?= null,
) : Serializable