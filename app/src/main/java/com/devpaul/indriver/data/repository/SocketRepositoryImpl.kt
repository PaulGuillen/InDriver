package com.devpaul.indriver.data.repository

import com.devpaul.indriver.domain.repository.SocketRepository
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class SocketRepositoryImpl(private val socket: Socket) : SocketRepository {

    override fun connect() {
        socket.connect()
    }

    override fun disconnect() {
        socket.disconnect()
    }

    override fun emitEvent(event: String, data: JSONObject) {
        socket.emit(event, data)
    }

    override fun setEventListener(event: String, listener: Emitter.Listener) {
        socket.on(event, listener)
    }
}