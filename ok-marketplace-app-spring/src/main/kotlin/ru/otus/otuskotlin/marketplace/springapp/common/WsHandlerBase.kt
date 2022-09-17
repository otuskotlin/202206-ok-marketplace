package ru.otus.otuskotlin.marketplace.springapp.common

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

abstract class WsHandlerBase(
    protected open val processor: MkplAdProcessor,
    protected open val sessions: MutableMap<String, SpringWsSession>,
    protected open val fromTransport: MkplContext.(request: String) -> Unit,
    protected open val toTransportBiz: MkplContext.() -> String,
    protected open val toTransportInit: MkplContext.() -> String,
) : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val clientSession = SpringWsSession(session)
        sessions[session.id] = clientSession

        val ctx = MkplContext(
            timeStart = Clock.System.now()
        )

        session.sendMessage(TextMessage(ctx.toTransportInit()))
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage)  {
        val ctx = MkplContext(timeStart = Clock.System.now())

        runBlocking {
            try {
                ctx.fromTransport(message.payload)
                processor.exec(ctx)
                ctx.toTransportBiz()

                if (ctx.isUpdatableCommand()) {
                    sessions.values.forEach {
                        it.fwSession.sendMessage(TextMessage(ctx.toTransportBiz()))
                    }
                } else {
                    session.sendMessage(TextMessage(ctx.toTransportBiz()))
                }
            } catch (e: Exception) {
                ctx.errors.add(e.asMkplError())
                session.sendMessage(TextMessage(ctx.toTransportInit()))
            }
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session.id)
    }

    private fun MkplContext.isUpdatableCommand() =
        this.command in listOf(MkplCommand.CREATE, MkplCommand.UPDATE, MkplCommand.DELETE)
}
