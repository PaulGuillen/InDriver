package com.devpaul.indriver.domain.model.res

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
    @SerializedName("user")
    val user: User? = null,
    @SerializedName("token")
    val token: String? = null,
) : Serializable {
    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(data: String?): LoginResponse? {
            return if (data.isNullOrEmpty()) {
                null
            } else {
                Gson().fromJson(data, LoginResponse::class.java)
            }
        }
    }
}

data class User(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("notification_token")
    val notificationToken: String? = null,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("roles")
    val roles: List<Role>? = null
) : Serializable {
    fun toJson(): String = Gson().toJson(
        User(
            name = name,
            lastname = lastname,
            email = email,
            phone = phone
        )
    )

    companion object {
        fun fromJson(data: String?): User? {
            return if (data.isNullOrEmpty()) {
                null
            } else {
                Gson().fromJson(data, User::class.java)
            }
        }
    }
}

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