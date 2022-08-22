package ru.otus.otuskotlin.marketplace.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.backend.services.OfferService
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportOffers

suspend fun ApplicationCall.offersAd(offerService: OfferService) {
    val offersAdRequest = receive<AdOffersRequest>()
    respond(
        MkplContext().apply { fromTransport(offersAdRequest) }.let {
            offerService.searchOffers(it, ::buildError)
        }.toTransportOffers()
    )
}
