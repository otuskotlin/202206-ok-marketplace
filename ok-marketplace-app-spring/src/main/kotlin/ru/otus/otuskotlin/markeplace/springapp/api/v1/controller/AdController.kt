package ru.otus.otuskotlin.markeplace.springapp.api.v1.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.markeplace.springapp.api.controllerAction
import ru.otus.otuskotlin.markeplace.springapp.api.v1.service.AdService
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v1.*

@RestController
@RequestMapping("v1/ad")
class AdController(
    private val adService: AdService
) {
    @PostMapping("create")
    fun createAd(@RequestBody createAdRequest: AdCreateRequest): AdCreateResponse = controllerAction(
        createAdRequest,
        MkplContext::fromTransport,
        adService::createAd,
        MkplContext::toTransportCreate
    )

    @PostMapping("read")
    fun readAd(@RequestBody readAdRequest: AdReadRequest) =
        controllerAction(readAdRequest, MkplContext::fromTransport, adService::readAd, MkplContext::toTransportRead)

    @RequestMapping("update", method = [RequestMethod.POST])
    fun updateAd(@RequestBody updateAdRequest: AdUpdateRequest): AdUpdateResponse = controllerAction(
        updateAdRequest,
        MkplContext::fromTransport,
        adService::updateAd,
        MkplContext::toTransportUpdate
    )

    @PostMapping("delete")
    fun deleteAd(@RequestBody deleteAdRequest: AdDeleteRequest): AdDeleteResponse = controllerAction(
        deleteAdRequest,
        MkplContext::fromTransport,
        adService::deleteAd,
        MkplContext::toTransportDelete
    )

    @PostMapping("search")
    fun searchAd(@RequestBody searchAdRequest: AdSearchRequest) = controllerAction(
        searchAdRequest,
        MkplContext::fromTransport,
        adService::searchAd,
        MkplContext::toTransportSearch
    )
}