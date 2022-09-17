package ru.otus.otuskotlin.marketplace.springapp.api.v1.controller

import org.springframework.stereotype.Component
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportAd
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportInit
import ru.otus.otuskotlin.marketplace.springapp.common.SpringWsSession
import ru.otus.otuskotlin.marketplace.springapp.common.WsHandlerBase

@Component
class WsAdHandlerV1(
    override val processor: MkplAdProcessor,
    override val sessions: MutableMap<String, SpringWsSession>,
) : WsHandlerBase(
    processor = processor,
    sessions = sessions,
    fromTransport = { fromTransport(apiV1Mapper.readValue(it, IRequest::class.java)) },
    toTransportBiz = { apiV1Mapper.writeValueAsString(toTransportAd()) },
    toTransportInit = { apiV1Mapper.writeValueAsString(toTransportInit()) },
)
