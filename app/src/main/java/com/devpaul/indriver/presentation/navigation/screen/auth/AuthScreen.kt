package com.devpaul.indriver.presentation.navigation.screen.auth

sealed class AuthScreen(val routes: String) {
    data object Login : AuthScreen("/login")
    data object Register : AuthScreen("/register")
    data object ForgotPassword : AuthScreen("/forgot_password")
}