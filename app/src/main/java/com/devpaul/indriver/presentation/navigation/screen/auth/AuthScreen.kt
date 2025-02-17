package com.devpaul.indriver.presentation.navigation.screen.auth

sealed class AuthScreen(val routes: String) {
    object Login : AuthScreen("/login")
    object Register : AuthScreen("/register")
    object ForgotPassword : AuthScreen("/forgot_password")
}