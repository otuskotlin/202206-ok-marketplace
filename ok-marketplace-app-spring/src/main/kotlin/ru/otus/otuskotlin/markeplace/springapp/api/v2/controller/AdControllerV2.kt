package ru.otus.otuskotlin.markeplace.springapp.api.v2.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v2.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

@RestController
@RequestMapping("v2/ad")
class AdControllerV2 {

    @PostMapping("create")
    fun createAd(@RequestBody request: AdCreateRequest): AdCreateResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.adResponse = MkplAdStub.get()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readAd(@RequestBody request: AdReadRequest): AdReadResponse  {
        val context = MkplContext()
        context.fromTransport(request)
        context.adResponse = MkplAdStub.get()
        return context.toTransportRead()
    }

    @PostMapping("update")
    fun updateAd(@RequestBody request: AdUpdateRequest): AdUpdateResponse  {
        val context = MkplContext()
        context.fromTransport(request)
        context.adResponse = MkplAdStub.get()
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteAd(@RequestBody request: AdDeleteRequest): AdDeleteResponse  {
        val context = MkplContext()
        context.fromTransport(request)
        context.adResponse = MkplAdStub.get()
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchAd(@RequestBody request: AdSearchRequest): AdSearchResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.adsResponse.add(MkplAdStub.get())
        return context.toTransportSearch()
    }
}