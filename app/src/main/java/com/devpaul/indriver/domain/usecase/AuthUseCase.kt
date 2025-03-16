package com.devpaul.indriver.domain.usecase

data class AuthUseCase(
    val login: LoginUC,
    val register: RegisterUC,
    val saveSession: SaveSessionUC,
    val getSession: GetSessionUC,
    val logOutUC: LogOutUC,
)