package com.devpaul.indriver.domain.usecase.socket

import com.devpaul.indriver.domain.repository.SocketRepository
import com.google.gson.JsonObject

class EmitSocketUC(private val socketRepository: SocketRepository) {

    operator fun invoke(event: String, data: JsonObject) {
        socketRepository.emitEvent(event, data)
    }
}