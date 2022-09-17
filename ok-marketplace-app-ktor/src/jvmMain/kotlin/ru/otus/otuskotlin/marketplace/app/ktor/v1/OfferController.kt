package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand


suspend fun ApplicationCall.offersAd(processor: MkplAdProcessor) =
    processV1<AdOffersRequest, AdOffersResponse>(processor, MkplCommand.OFFERS)
