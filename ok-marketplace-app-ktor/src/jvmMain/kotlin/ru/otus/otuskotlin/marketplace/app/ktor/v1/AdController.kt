package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

suspend fun ApplicationCall.createAd(processor: MkplAdProcessor) =
    processV1<AdCreateRequest, AdCreateResponse>(processor, MkplCommand.CREATE)

suspend fun ApplicationCall.readAd(processor: MkplAdProcessor) =
    processV1<AdReadRequest, AdReadResponse>(processor, MkplCommand.READ)

suspend fun ApplicationCall.updateAd(processor: MkplAdProcessor) =
    processV1<AdUpdateRequest, AdUpdateResponse>(processor, MkplCommand.UPDATE)

suspend fun ApplicationCall.deleteAd(processor: MkplAdProcessor) =
    processV1<AdDeleteRequest, AdDeleteResponse>(processor, MkplCommand.DELETE)

suspend fun ApplicationCall.searchAd(processor: MkplAdProcessor) =
    processV1<AdSearchRequest, AdSearchResponse>(processor, MkplCommand.SEARCH)
