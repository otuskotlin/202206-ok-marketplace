package ru.otus.otuskotlin.marketplace.app.ktor.v2

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportOffers

suspend fun ApplicationCall.offersAd(processor: MkplAdProcessor) =
    process<AdOffersRequest, AdOffersResponse>(processor, { fromTransport(it) }, { toTransportOffers() })
