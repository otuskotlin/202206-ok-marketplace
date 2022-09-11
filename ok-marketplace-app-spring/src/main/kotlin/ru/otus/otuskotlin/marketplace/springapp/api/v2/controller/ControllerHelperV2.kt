package ru.otus.otuskotlin.marketplace.springapp.api.v2.controller

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

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> processV2(
    processor: MkplAdProcessor,
    command: MkplCommand? = null,
    requestString: String,
): String {
    val ctx = MkplContext(
        timeStart = Clock.System.now(),
    )
    return try {
        val request = apiV2Mapper.decodeFromString<Q>(requestString)
        ctx.fromTransport(request)
        processor.exec(ctx)
        apiV2Mapper.encodeToString(ctx.toTransportAd())
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = MkplState.FAILING
        ctx.errors.add(e.asMkplError())
        processor.exec(ctx)
        apiV2Mapper.encodeToString(ctx.toTransportAd())
    }
}
