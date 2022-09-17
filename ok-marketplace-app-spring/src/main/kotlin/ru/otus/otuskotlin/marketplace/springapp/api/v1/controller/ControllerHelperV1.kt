package ru.otus.otuskotlin.marketplace.springapp.api.v1.controller

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.IResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportAd

suspend inline fun <reified Q : IRequest, reified R : IResponse> processV1(
    processor: MkplAdProcessor,
    command: MkplCommand? = null,
    request: Q,
): R {
    val ctx = MkplContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.fromTransport(request)
        processor.exec(ctx)
        ctx.toTransportAd() as R
    } catch (e: Throwable) {
        command?.also { ctx.command = it }
        ctx.state = MkplState.FAILING
        ctx.errors.add(e.asMkplError())
        processor.exec(ctx)
        ctx.toTransportAd() as R
    }
}
