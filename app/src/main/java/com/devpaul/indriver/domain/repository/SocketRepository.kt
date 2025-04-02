package com.devpaul.indriver.domain.repository

import io.socket.emitter.Emitter
import org.json.JSONObject

interface SocketRepository {

    fun connect()

    fun disconnect()

    fun emitEvent(event: String, data: JSONObject)

    fun setEventListener(event: String, listener: Emitter.Listener)
}