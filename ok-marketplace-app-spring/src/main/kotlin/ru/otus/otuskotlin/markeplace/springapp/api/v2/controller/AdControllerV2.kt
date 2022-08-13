package ru.otus.otuskotlin.markeplace.springapp.api.v2.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.markeplace.springapp.api.controllerAction
import ru.otus.otuskotlin.markeplace.springapp.api.v2.service.AdServiceV2
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v2.*

@RestController
@RequestMapping("v2/ad")
class AdControllerV2(
    private val adServiceV2: AdServiceV2
) {

    @PostMapping("create")
    fun createAd(@RequestBody createAdRequest: AdCreateRequest): AdCreateResponse =
        controllerAction(createAdRequest, MkplContext::fromTransport, adServiceV2::createAd, MkplContext::toTransportCreate)

    @PostMapping("read")
    fun readAd(@RequestBody readAdRequest: AdReadRequest): AdReadResponse =
        controllerAction(readAdRequest, MkplContext::fromTransport, adServiceV2::readAd, MkplContext::toTransportRead)

    @PostMapping("update")
    fun updateAd(@RequestBody updateAdRequest: AdUpdateRequest): AdUpdateResponse =
        controllerAction(updateAdRequest, MkplContext::fromTransport, adServiceV2::updateAd, MkplContext::toTransportUpdate)

    @PostMapping("delete")
    fun deleteAd(@RequestBody deleteAdRequest: AdDeleteRequest): AdDeleteResponse =
        controllerAction(deleteAdRequest, MkplContext::fromTransport, adServiceV2::deleteAd, MkplContext::toTransportDelete)

    @PostMapping("search")
    fun searchAd(@RequestBody searchAdRequest: AdSearchRequest) =
        controllerAction(searchAdRequest, MkplContext::fromTransport, adServiceV2::searchAd, MkplContext::toTransportCreate)
}