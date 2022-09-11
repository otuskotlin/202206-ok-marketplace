package ru.otus.otuskotlin.marketplace.springapp.api.v1.controller

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportOffers

@RestController
@RequestMapping("v1/ad")
class OfferController(
    private val processor: MkplAdProcessor = MkplAdProcessor()
) {

    @PostMapping("offers")
    fun searchOffers(@RequestBody request: AdOffersRequest): AdOffersResponse {
        val ctx = MkplContext(
            timeStart = Clock.System.now(),
        )
        ctx.fromTransport(request)
        runBlocking { processor.exec(ctx) }
        return ctx.toTransportOffers()
    }
}
