package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request: IRequest = AdCreateRequest(
        requestType = "create",
        requestId = "123",
        debug = AdDebug(
            mode = AdRequestDebugMode.STUB,
            stub = AdRequestDebugStubs.BAD_TITLE
        ),
        ad = AdCreateObject(
            title = "ad title",
            description = "ad description",
            adType = DealSide.DEMAND,
            visibility = AdVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
        val json = apiV2Mapper.encodeToString(request)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"ad title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV2Mapper.encodeToString(request)
//        val json = apiV2Mapper.encodeToString(AdRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
//        val obj = apiV2Mapper.decodeFromString(AdRequestSerializer, json) as AdCreateRequest
        val obj = apiV2Mapper.decodeFromString(json) as AdCreateRequest

        assertEquals(request, obj)
    }
}
