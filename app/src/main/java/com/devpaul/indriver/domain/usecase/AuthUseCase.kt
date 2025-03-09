package com.devpaul.indriver.domain.usecase

data class AuthUseCase(
    val login: LoginUC,
    val register: RegisterUC
)