package com.devpaul.indriver.domain.usecase.auth

import com.devpaul.indriver.domain.usecase.user.GetSessionUC
import com.devpaul.indriver.domain.usecase.user.SaveSessionUC
import com.devpaul.indriver.domain.usecase.user.UpdateSessionUC

data class AuthUseCases(
    val login: LoginUC,
    val register: RegisterUC,
    val saveSession: SaveSessionUC,
    val getSession: GetSessionUC,
    val logOutUC: LogOutUC,
    val updateSessionUC: UpdateSessionUC,
)