package com.devpaul.indriver.presentation.screens.auth.register

data class RegisterState(
    val name : String = "",
    val lastName: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)