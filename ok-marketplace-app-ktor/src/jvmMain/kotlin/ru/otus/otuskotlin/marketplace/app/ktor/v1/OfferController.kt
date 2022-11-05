package ru.otus.otuskotlin.marketplace.app.ktor.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdOffersResponse
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand
import ru.otus.otuskotlin.marketplace.logging.common.IMpLogWrapper


suspend fun ApplicationCall.offersAd(processor: MkplAdProcessor, logger: IMpLogWrapper) =
    processV1<AdOffersRequest, AdOffersResponse>(processor, logger, "ad-offers", MkplCommand.OFFERS)
