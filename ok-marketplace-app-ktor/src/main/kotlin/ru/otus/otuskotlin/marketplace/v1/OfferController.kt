package ru.otus.otuskotlin.marketplace.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportOffers
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

suspend fun ApplicationCall.offersAd() {
    val request = receive<AdOffersRequest>()
    val context = MkplContext()
    context.fromTransport(request)
    context.adsResponse.addAll(MkplAdStub.prepareOffersList("Болт", MkplDealSide.SUPPLY))
    respond(context.toTransportOffers())
}
