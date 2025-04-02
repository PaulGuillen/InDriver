package com.devpaul.indriver.domain.usecase.socket

import com.devpaul.indriver.domain.repository.SocketRepository
import org.json.JSONObject

class EmitSocketUC(private val socketRepository: SocketRepository) {

    operator fun invoke(event: String, data: JSONObject) {
        socketRepository.emitEvent(event, data)
    }
}