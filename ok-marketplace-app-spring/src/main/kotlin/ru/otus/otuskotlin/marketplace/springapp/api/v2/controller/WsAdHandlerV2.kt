package ru.otus.otuskotlin.marketplace.springapp.api.v2.controller

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.springframework.stereotype.Component
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportAd
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportInit
import ru.otus.otuskotlin.marketplace.springapp.common.SpringWsSession
import ru.otus.otuskotlin.marketplace.springapp.common.WsHandlerBase

@Component
class WsAdHandlerV2(
    override val processor: MkplAdProcessor,
    override val sessions: MutableMap<String, SpringWsSession>,
) : WsHandlerBase(
    processor = processor,
    sessions = sessions,
    fromTransport = { fromTransport(apiV2Mapper.decodeFromString(it) as IRequest) },
    toTransportBiz = { apiV2Mapper.encodeToString(toTransportAd()) },
    toTransportInit = { apiV2Mapper.encodeToString(toTransportInit() as IResponse) },
    apiVersion = "v2",
)
