package com.devpaul.indriver.domain.model.res

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
    @SerializedName("user")
    val user: User?= null,
    @SerializedName("token")
    val token: String ?= null,
) : Serializable

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("notification_token")
    val notificationToken: String?,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("roles")
    val roles: List<Role>
) : Serializable

data class Role(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("route")
    val route: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) : Serializable