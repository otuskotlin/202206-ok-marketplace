package ru.otus.otuskotlin.marketplace.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.backend.services.OfferService
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

suspend fun ApplicationCall.offersAd(service: OfferService) =
    controllerHelperV1<AdOffersRequest, AdOffersResponse>(MkplCommand.OFFERS) {
        service.searchOffers(this, ::buildError)
    }
