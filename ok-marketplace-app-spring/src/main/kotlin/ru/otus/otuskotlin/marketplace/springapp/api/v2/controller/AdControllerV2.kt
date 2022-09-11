package ru.otus.otuskotlin.marketplace.springapp.api.v2.controller

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

@RestController
@RequestMapping("v2/ad")
class AdControllerV2(
    private val processor: MkplAdProcessor,
) {

    @PostMapping("create")
    suspend fun createAd(@RequestBody request: String): String =
        processV2<AdCreateRequest, AdCreateResponse>(processor, MkplCommand.CREATE, requestString = request)

    @PostMapping("read")
    suspend fun readAd(@RequestBody request: String): String =
        processV2<AdReadRequest, AdReadResponse>(processor, MkplCommand.READ, requestString = request)

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun updateAd(@RequestBody request: String): String =
        processV2<AdUpdateRequest, AdUpdateResponse>(processor, MkplCommand.UPDATE, requestString = request)

    @PostMapping("delete")
    suspend fun deleteAd(@RequestBody request: String): String =
        processV2<AdDeleteRequest, AdDeleteResponse>(processor, MkplCommand.DELETE, requestString = request)

    @PostMapping("search")
    suspend fun searchAd(@RequestBody request: String): String =
        processV2<AdSearchRequest, AdSearchResponse>(processor, MkplCommand.SEARCH, requestString = request)
}
