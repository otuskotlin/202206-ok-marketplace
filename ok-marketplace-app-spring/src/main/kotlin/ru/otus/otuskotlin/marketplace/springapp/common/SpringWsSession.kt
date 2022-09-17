package ru.otus.otuskotlin.marketplace.springapp.common

import org.springframework.web.socket.WebSocketSession
import ru.otus.otuskotlin.marketplace.common.models.IClientSession

class SpringWsSession(
    override val fwSession: WebSocketSession,
) : IClientSession<WebSocketSession>
