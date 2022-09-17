package ru.otus.otuskotlin.marketplace.app.ktor.v2

import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorUserSession
import ru.otus.otuskotlin.marketplace.app.ktor.base.mpWsHandler
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportAd
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportInit

suspend fun WebSocketSession.mpWsHandlerV2(
    processor: MkplAdProcessor,
    sessions: MutableSet<KtorUserSession>
) = this.mpWsHandler(
    processor = processor,
    sessions = sessions,
    fromTransport = { fromTransport(apiV2Mapper.decodeFromString(it) as IRequest) },
    toTransportInit = { apiV2Mapper.encodeToString(toTransportInit() as IResponse) },
    toTransportBiz = { apiV2Mapper.encodeToString(toTransportAd()) },
)

