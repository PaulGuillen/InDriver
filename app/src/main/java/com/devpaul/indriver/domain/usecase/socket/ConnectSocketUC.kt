package com.devpaul.indriver.domain.usecase.socket

import com.devpaul.indriver.domain.repository.SocketRepository

class ConnectSocketUC(private val socketRepository: SocketRepository) {

    operator fun invoke() {
        socketRepository.connect()
    }
}