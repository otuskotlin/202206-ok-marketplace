package ru.otus.otuskotlin.marketplace.app.ktor.v2

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper

suspend fun ApplicationCall.createAd(processor: MkplAdProcessor, logger: IMpLogWrapper) =
    processV2<AdCreateRequest, AdCreateResponse>(processor, logger, "ad-create", MkplCommand.CREATE)

suspend fun ApplicationCall.readAd(processor: MkplAdProcessor, logger: IMpLogWrapper) =
    processV2<AdReadRequest, AdReadResponse>(processor, logger, "ad-read", MkplCommand.READ)


suspend fun ApplicationCall.updateAd(processor: MkplAdProcessor, logger: IMpLogWrapper) =
    processV2<AdUpdateRequest, AdUpdateResponse>(processor, logger, "ad-update", MkplCommand.UPDATE)

suspend fun ApplicationCall.deleteAd(processor: MkplAdProcessor, logger: IMpLogWrapper) =
    processV2<AdDeleteRequest, AdDeleteResponse>(processor, logger, "ad-delete", MkplCommand.DELETE)

suspend fun ApplicationCall.searchAd(processor: MkplAdProcessor, logger: IMpLogWrapper) =
    processV2<AdSearchRequest, AdSearchResponse>(processor, logger, "ad-search", MkplCommand.SEARCH)
