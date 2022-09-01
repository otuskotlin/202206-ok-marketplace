package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor

suspend inline fun <reified T : Any, reified R : Any> ApplicationCall.process(
    processor: MkplAdProcessor,
    fromTransport: MkplContext.(request: T) -> Unit,
    toTransport: MkplContext.() -> R
) {
    val ctx = MkplContext(
        timeStart = Clock.System.now(),
    )
    val request = receive<T>()
    ctx.fromTransport(request)
    processor.exec(ctx)
    respond(ctx.toTransport())
}

suspend fun ApplicationCall.createAd(processor: MkplAdProcessor) =
    process<AdCreateRequest, AdCreateResponse>(processor, { fromTransport(it) }, { toTransportCreate()})

suspend fun ApplicationCall.readAd(processor: MkplAdProcessor) =
    process<AdReadRequest, AdReadResponse>(processor, { fromTransport(it) }, { toTransportRead()})

suspend fun ApplicationCall.updateAd(processor: MkplAdProcessor) =
    process<AdUpdateRequest, AdUpdateResponse>(processor, { fromTransport(it) }, { toTransportUpdate()})

suspend fun ApplicationCall.deleteAd(processor: MkplAdProcessor) =
    process<AdDeleteRequest, AdDeleteResponse>(processor, { fromTransport(it) }, { toTransportDelete()})

suspend fun ApplicationCall.searchAd(processor: MkplAdProcessor) =
    process<AdSearchRequest, AdSearchResponse>(processor, { fromTransport(it) }, { toTransportSearch()})
