package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.websocket.*
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorUserSession
import ru.otus.otuskotlin.marketplace.app.ktor.base.mpWsHandler
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportAd
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportInit

suspend fun WebSocketSession.mpWsHandlerV1(
    processor: MkplAdProcessor,
    sessions: MutableSet<KtorUserSession>
) = this.mpWsHandler(
    processor = processor,
    sessions = sessions,
    fromTransport = { fromTransport(apiV1Mapper.readValue(it, IRequest::class.java)) },
    toTransportInit = { apiV1Mapper.writeValueAsString(toTransportInit()) },
    toTransportBiz = { apiV1Mapper.writeValueAsString(toTransportAd()) },
)
