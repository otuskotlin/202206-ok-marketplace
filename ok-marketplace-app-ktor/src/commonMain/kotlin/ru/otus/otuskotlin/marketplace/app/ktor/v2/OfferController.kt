package ru.otus.otuskotlin.marketplace.app.ktor.v2

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

suspend fun ApplicationCall.offersAd(processor: MkplAdProcessor) =
    processV2<AdOffersRequest, AdOffersResponse>(processor, MkplCommand.OFFERS)
