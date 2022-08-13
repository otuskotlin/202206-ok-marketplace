package ru.otus.otuskotlin.markeplace.springapp.api.v2.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.markeplace.springapp.api.controllerAction
import ru.otus.otuskotlin.markeplace.springapp.api.v2.service.OfferServiceV2
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportOffers

@RestController
@RequestMapping("v2/ad")
class OfferControllerV2(
    private val offerServiceV2: OfferServiceV2
) {

    @PostMapping("offers")
    fun searchOffers(@RequestBody offersAdRequest: AdOffersRequest) =
        controllerAction(offersAdRequest, MkplContext::fromTransport, offerServiceV2::searchOffers, MkplContext::toTransportOffers)
}