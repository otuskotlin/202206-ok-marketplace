package ru.otus.otuskotlin.marketplace.app.ktor.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportAd

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV2(
    processor: MkplAdProcessor,
    command: MkplCommand? = null,
) {
    val ctx = MkplContext(
        timeStart = Clock.System.now(),
    )
    try {
        val request = apiV2Mapper.decodeFromString<Q>(receiveText())
        ctx.fromTransport(request)
        processor.exec(ctx)
        respond(apiV2Mapper.encodeToString(ctx.toTransportAd()))
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = MkplState.FAILING
        ctx.errors.add(e.asMkplError())
        processor.exec(ctx)
        respond(apiV2Mapper.encodeToString(ctx.toTransportAd()))
    }
}
