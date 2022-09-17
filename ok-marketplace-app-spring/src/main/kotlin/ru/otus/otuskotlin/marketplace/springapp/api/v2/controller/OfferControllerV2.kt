package ru.otus.otuskotlin.marketplace.springapp.api.v2.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

@RestController
@RequestMapping("v2/ad")
class OfferControllerV2(
    private val processor: MkplAdProcessor = MkplAdProcessor()
) {

    @PostMapping("offers")
    suspend fun searchOffers(@RequestBody request: String): String =
        processV2<AdOffersRequest, AdOffersResponse>(processor, MkplCommand.OFFERS, requestString = request)
}
