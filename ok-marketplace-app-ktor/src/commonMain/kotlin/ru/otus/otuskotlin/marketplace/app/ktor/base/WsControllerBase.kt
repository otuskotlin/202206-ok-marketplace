package ru.otus.otuskotlin.marketplace.app.ktor.base

import io.ktor.util.logging.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.addError
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

suspend fun WebSocketSession.mpWsHandler(
    processor: MkplAdProcessor,
    sessions: MutableSet<KtorUserSession>,
    fromTransport: MkplContext.(request: String) -> Unit,
    toTransportBiz: MkplContext.() -> String,
    toTransportInit: MkplContext.() -> String,
    apiVersion: String,
) {
    val userSession = KtorUserSession(this, apiVersion)
    sessions.add(userSession)
    run {
        val ctx = MkplContext(
            timeStart = Clock.System.now()
        )
        // обработка запроса на инициализацию
        outgoing.send(Frame.Text(ctx.toTransportInit()))
    }
    incoming
        .receiveAsFlow()
        .mapNotNull { it as? Frame.Text }
        .map { frame ->
            val ctx = MkplContext(
                timeStart = Clock.System.now()
            )
            // Обработка исключений без завершения flow
            try {
                val jsonStr = frame.readText()
                ctx.fromTransport(jsonStr)
                processor.exec(ctx)
                // Если произошли изменения, то ответ отправляется всем
                if (ctx.isUpdatableCommand()) {
                    sessions.filter { it.apiVersion == apiVersion }.forEach {
                        it.fwSession.send(Frame.Text(ctx.toTransportBiz()))
                    }
                } else {
                    outgoing.send(Frame.Text(ctx.toTransportBiz()))
                }
            } catch (_: ClosedReceiveChannelException) {
                sessions.remove(userSession)
            } catch (t: Throwable) {
                ctx.addError(t.asMkplError())
                outgoing.send(Frame.Text(ctx.toTransportInit()))
            }
        }
        .collect()
}

private fun MkplContext.isUpdatableCommand() =
    this.command in listOf(MkplCommand.CREATE, MkplCommand.UPDATE, MkplCommand.DELETE)
