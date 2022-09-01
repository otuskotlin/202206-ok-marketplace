package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportOffers


suspend fun ApplicationCall.offersAd(processor: MkplAdProcessor) =
    process<AdOffersRequest, AdOffersResponse>(processor, { fromTransport(it) }, { toTransportOffers()})
