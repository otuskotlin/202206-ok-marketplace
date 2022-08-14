package ru.otus.otuskotlin.markeplace.springapp.api.v1.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v1.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

@RestController
@RequestMapping("v1/ad")
class AdController {
    @PostMapping("create")
    fun createAd(@RequestBody request: AdCreateRequest): AdCreateResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.adResponse = MkplAdStub.get()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readAd(@RequestBody request: AdReadRequest): AdReadResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.adResponse = MkplAdStub.get()
        return context.toTransportRead()
    }

    @RequestMapping("update", method = [RequestMethod.POST])
    fun updateAd(@RequestBody request: AdUpdateRequest): AdUpdateResponse {
        val context = MkplContext()
        context.fromTransport(request)
        context.adResponse = MkplAdStub.get()
        return context.toTransportUpdate()
    }

    @PostMapping("delete")
    fun deleteAd(@RequestBody request: AdDeleteRequest): AdDeleteResponse {
        val context = MkplContext()
        context.fromTransport(request)
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchAd(@RequestBody request: AdSearchRequest): AdSearchResponse  {
        val context = MkplContext()
        context.fromTransport(request)
        context.adsResponse.add(MkplAdStub.get())
        return context.toTransportSearch()
    }
}