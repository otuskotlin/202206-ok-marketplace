package ru.otus.otuskotlin.markeplace.springapp.api.v2.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportOffers
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

@RestController
@RequestMapping("v2/ad")
class OfferControllerV2 {

    @PostMapping("offers")
    fun searchOffers(@RequestBody request: AdOffersRequest): AdOffersResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.adsResponse.add(MkplAdStub.get())
        return context.toTransportOffers()
    }
}