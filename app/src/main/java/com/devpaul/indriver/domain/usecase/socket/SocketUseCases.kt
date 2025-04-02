package com.devpaul.indriver.domain.usecase.socket

data class SocketUseCases(
    val connectSocketUC: ConnectSocketUC,
    val disconnectSocketUC: DisconnectSocketUC,
    val emitSocketUC: EmitSocketUC,
    val setEventListenerSocketUC: SetEventListenerSocketUC,
)