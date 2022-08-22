package ru.otus.otuskotlin.marketplace.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.backend.services.AdService
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

suspend fun ApplicationCall.createAd(service: AdService) =
    controllerHelperV1<AdCreateRequest, AdCreateResponse>(MkplCommand.READ) {
        service.readAd(this, ::buildError)
    }

suspend fun ApplicationCall.readAd(service: AdService) =
    controllerHelperV1<AdReadRequest, AdReadResponse>(MkplCommand.READ) {
        service.readAd(this, ::buildError)
    }

suspend fun ApplicationCall.updateAd(service: AdService) =
    controllerHelperV1<AdUpdateRequest, AdUpdateResponse>(MkplCommand.UPDATE) {
        service.updateAd(this, ::buildError)
    }

suspend fun ApplicationCall.deleteAd(service: AdService) =
    controllerHelperV1<AdDeleteRequest, AdDeleteResponse>(MkplCommand.DELETE) {
        service.deleteAd(this, ::buildError)
    }

suspend fun ApplicationCall.searchAd(adService: AdService) =
    controllerHelperV1<AdSearchRequest, AdSearchResponse>(MkplCommand.SEARCH) {
        adService.searchAd(this, ::buildError)
    }
