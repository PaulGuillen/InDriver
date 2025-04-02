package com.devpaul.indriver.domain.repository

import com.google.gson.JsonObject
import io.socket.emitter.Emitter

interface SocketRepository {

    fun connect()

    fun disconnect()

    fun emitEvent(event: String, data: JsonObject)

    fun setEventListener(event: String, listener: Emitter.Listener)
}