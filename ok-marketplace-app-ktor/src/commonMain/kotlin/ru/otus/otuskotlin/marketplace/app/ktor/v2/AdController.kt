package ru.otus.otuskotlin.marketplace.app.ktor.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v2.*

suspend inline fun <reified T, reified R> ApplicationCall.process(
    processor: MkplAdProcessor,
    fromTransport: MkplContext.(request: T) -> Unit,
    toTransport: MkplContext.() -> R
) {
    val ctx = MkplContext(
        timeStart = Clock.System.now(),
    )
    val request = apiV2Mapper.decodeFromString<T>(receiveText())
    ctx.fromTransport(request)
    processor.exec(ctx)
    respond(apiV2Mapper.encodeToString(ctx.toTransport()))
}

suspend fun ApplicationCall.createAd(processor: MkplAdProcessor) =
    process<AdCreateRequest, AdCreateResponse>(processor, { fromTransport(it) }, { toTransportCreate() })

suspend fun ApplicationCall.readAd(processor: MkplAdProcessor) =
    process<AdReadRequest, AdReadResponse>(processor, { fromTransport(it) }, { toTransportRead() })


suspend fun ApplicationCall.updateAd(processor: MkplAdProcessor) =
    process<AdUpdateRequest, AdUpdateResponse>(processor, { fromTransport(it) }, { toTransportUpdate() })

suspend fun ApplicationCall.deleteAd(processor: MkplAdProcessor) =
    process<AdDeleteRequest, AdDeleteResponse>(processor, { fromTransport(it) }, { toTransportDelete() })

suspend fun ApplicationCall.searchAd(processor: MkplAdProcessor) =
    process<AdSearchRequest, AdSearchResponse>(processor, { fromTransport(it) }, { toTransportSearch() })
