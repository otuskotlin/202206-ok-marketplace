package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.backend.services.OfferService
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportOffers

suspend fun ApplicationCall.offersAd(offerService: OfferService) {
    val offersAdRequest = receive<AdOffersRequest>()
    respond(
        MkplContext().apply { fromTransport(offersAdRequest) }.let {
            offerService.searchOffers(it, ::buildError)
        }.toTransportOffers()
    )
}
