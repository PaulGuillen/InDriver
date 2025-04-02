package com.devpaul.indriver.domain.usecase.socket

import com.devpaul.indriver.domain.repository.SocketRepository
import com.google.gson.JsonObject
import io.socket.emitter.Emitter

class SetEventListenerSocketUC(private val socketRepository: SocketRepository) {

    operator fun invoke(event: String, listener: Emitter.Listener) {
        socketRepository.setEventListener(event, listener)
    }
}