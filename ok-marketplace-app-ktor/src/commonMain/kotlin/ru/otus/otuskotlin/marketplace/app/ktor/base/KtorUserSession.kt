package ru.otus.otuskotlin.marketplace.app.ktor.base

import io.ktor.websocket.*
import ru.otus.otuskotlin.marketplace.common.models.IClientSession

data class KtorUserSession(
    override val fwSession: WebSocketSession,
    override val apiVersion: String,
): IClientSession<WebSocketSession>
