package com.devpaul.indriver.domain.usecase.socket

import com.devpaul.indriver.domain.repository.SocketRepository

class DisconnectSocketUC(private val socketRepository: SocketRepository) {

    operator fun invoke() {
        socketRepository.disconnect()
    }
}