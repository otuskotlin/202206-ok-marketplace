package ru.otus.otuskotlin.marketplace.springapp.api.v1.controller

import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplCommand

@RestController
@RequestMapping("v1/ad")
class AdController(
    private val processor: MkplAdProcessor,
) {
    // По документации suspend функции должны работать корректно.
    // На практике не работают.
    // Поэтому отключил на create suspend, остальные пока оставляю,
    // надеюсь разобраться позже

    @PostMapping("create")
    fun createAd(@RequestBody request: AdCreateRequest): AdCreateResponse = runBlocking {
        processV1<AdCreateRequest, AdCreateResponse>(processor, MkplCommand.CREATE, request = request)
    }

    @PostMapping("read")
    suspend fun readAd(@RequestBody request: AdReadRequest): AdReadResponse =
        processV1(processor, MkplCommand.READ, request = request)

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun updateAd(@RequestBody request: AdUpdateRequest): AdUpdateResponse =
        processV1(processor, MkplCommand.UPDATE, request = request)


    @PostMapping("delete")
    suspend fun deleteAd(@RequestBody request: AdDeleteRequest): AdDeleteResponse =
        processV1(processor, MkplCommand.DELETE, request = request)

    @PostMapping("search")
    suspend fun searchAd(@RequestBody request: AdSearchRequest): AdSearchResponse =
        processV1(processor, MkplCommand.SEARCH, request = request)
}
