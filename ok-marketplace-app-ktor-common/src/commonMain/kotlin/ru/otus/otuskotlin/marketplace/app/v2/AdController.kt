package ru.otus.otuskotlin.marketplace.app.v2

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.backend.services.AdService
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v2.*

suspend fun ApplicationCall.createAd(adService: AdService) {
    val createAdRequest = receive<AdCreateRequest>()
    respond(
        MkplContext().apply { fromTransport(createAdRequest) }.let {
            adService.createAd(it)
        }.toTransportCreate()
    )
}

suspend fun ApplicationCall.readAd(adService: AdService) {
    val readAdRequest = receive<AdReadRequest>()
    respond(
        MkplContext().apply { fromTransport(readAdRequest) }.let {
            adService.readAd(it, ::buildError)
        }.toTransportRead()
    )
}

suspend fun ApplicationCall.updateAd(adService: AdService) {
    val updateAdRequest = receive<AdUpdateRequest>()
    respond(
        MkplContext().apply { fromTransport(updateAdRequest) }.let {
            adService.updateAd(it, ::buildError)
        }.toTransportUpdate()
    )
}

suspend fun ApplicationCall.deleteAd(adService: AdService) {
    val deleteAdRequest = receive<AdDeleteRequest>()
    respond(
        MkplContext().apply { fromTransport(deleteAdRequest) }.let {
            adService.deleteAd(it, ::buildError)
        }.toTransportDelete()
    )
}

suspend fun ApplicationCall.searchAd(adService: AdService) {
    val searchAdRequest = receive<AdSearchRequest>()
    respond(
        MkplContext().apply { fromTransport(searchAdRequest) }.let {
            adService.searchAd(it, ::buildError)
        }.toTransportSearch()
    )
}
