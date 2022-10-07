package ru.otus.otuskotlin.marketplace.repo

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.app.ktor.module
import ru.otus.otuskotlin.marketplace.app.ktor.moduleJvm
import ru.otus.otuskotlin.marketplace.backend.repo.common.AdRepositoryMock
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.repo.DbAdResponse
import ru.otus.otuskotlin.marketplace.common.repo.DbAdsResponse
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class V1AdMockApiTest {
    @Test
    fun create() = testApplication {
        application {
            val repo by lazy {
                AdRepositoryMock(
                    invokeCreateAd = {
                        DbAdResponse(
                            isSuccess = true,
                            data = it.ad.copy(id = MkplAdId(UUID.randomUUID().toString())),
                        )
                    }
                )
            }
            val settigs by lazy { MkplSettings(repoTest = repo) }
            module()
            moduleJvm(settigs)
        }
        val client = myClient()

        val createAd = AdCreateObject(
            title = "Болт",
            description = "КРУТЕЙШИЙ",
            adType = DealSide.DEMAND,
            visibility = AdVisibility.PUBLIC,
        )

        val response = client.post("/v1/ad/create") {
            val requestObj = AdCreateRequest(
                requestId = "12345",
                ad = createAd,
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdCreateResponse>()
        assertEquals(200, response.status.value)
        assertNotNull(responseObj.ad?.id)
        assertEquals(createAd.title, responseObj.ad?.title)
        assertEquals(createAd.description, responseObj.ad?.description)
        assertEquals(createAd.adType, responseObj.ad?.adType)
        assertEquals(createAd.visibility, responseObj.ad?.visibility)
    }

    @Test
    fun read() = testApplication {
        application {
            val repo by lazy {
                AdRepositoryMock(
                    invokeReadAd = {
                        DbAdResponse(
                            isSuccess = true,
                            data = MkplAd(id = it.id),
                        )
                    }
                )
            }
            val settigs by lazy { MkplSettings(repoTest = repo) }
            module()
            moduleJvm(settigs)
        }
        val client = myClient()

        val adId = "666"

        val response = client.post("/v1/ad/read") {
            val requestObj = AdReadRequest(
                requestId = "12345",
                ad = AdReadObject(adId),
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(adId, responseObj.ad?.id)
    }

    @Test
    fun update() = testApplication {
        application {
            val repo by lazy {
                AdRepositoryMock(
                    invokeReadAd = {
                        DbAdResponse(
                            isSuccess = true,
                            data = MkplAd(id = it.id),
                        )
                    },
                    invokeUpdateAd = {
                        DbAdResponse(
                            isSuccess = true,
                            data = it.ad,
                        )
                    }
                )
            }
            val settigs by lazy { MkplSettings(repoTest = repo) }
            module()
            moduleJvm(settigs)
        }
        val client = myClient()

        val adUpdate = AdUpdateObject(
            id = "666",
            title = "Болт",
            description = "КРУТЕЙШИЙ",
            adType = DealSide.DEMAND,
            visibility = AdVisibility.PUBLIC,
        )

        val response = client.post("/v1/ad/update") {
            val requestObj = AdUpdateRequest(
                requestId = "12345",
                ad = AdUpdateObject(
                    id = "666",
                    title = "Болт",
                    description = "КРУТЕЙШИЙ",
                    adType = DealSide.DEMAND,
                    visibility = AdVisibility.PUBLIC,
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(adUpdate.id, responseObj.ad?.id)
        assertEquals(adUpdate.title, responseObj.ad?.title)
        assertEquals(adUpdate.description, responseObj.ad?.description)
        assertEquals(adUpdate.adType, responseObj.ad?.adType)
        assertEquals(adUpdate.visibility, responseObj.ad?.visibility)
    }

    @Test
    fun delete() = testApplication {
        application {
            val repo by lazy {
                AdRepositoryMock(
                    invokeReadAd = {
                        DbAdResponse(
                            isSuccess = true,
                            data = MkplAd(id = it.id),
                        )
                    },
                    invokeDeleteAd = {
                        DbAdResponse(
                            isSuccess = true,
                            data = MkplAd(id = it.id),
                        )
                    }
                )
            }
            val settigs by lazy { MkplSettings(repoTest = repo) }
            module()
            moduleJvm(settigs)
        }
        val client = myClient()

        val deleteId = "666"

        val response = client.post("/v1/ad/delete") {
            val requestObj = AdDeleteRequest(
                requestId = "12345",
                ad = AdDeleteObject(
                    id = deleteId,
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals(deleteId, responseObj.ad?.id)
    }

    @Test
    fun search() = testApplication {
        application {
            val repo by lazy {
                AdRepositoryMock(
                    invokeSearchAd = {
                        DbAdsResponse(
                            isSuccess = true,
                            data = listOf(
                                MkplAd(
                                    title = it.titleFilter,
                                    ownerId = it.ownerId,
                                    adType = it.dealSide,
                                )
                            ),
                        )
                    }
                )
            }
            val settigs by lazy { MkplSettings(repoTest = repo) }
            module()
            moduleJvm(settigs)
        }
        val client = myClient()

        val response = client.post("/v1/ad/search") {
            val requestObj = AdSearchRequest(
                requestId = "12345",
                adFilter = AdSearchFilter(),
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.ads?.size)
    }

    @Test
    fun offers() = testApplication {
        application {
            val repo by lazy {
                AdRepositoryMock(
                    invokeReadAd = {
                       DbAdResponse(
                           isSuccess = true,
                           data = MkplAd(id = it.id)
                       )
                    },
                    invokeSearchAd = {
                        DbAdsResponse(
                            isSuccess = true,
                            data = listOf(
                                MkplAd(
                                    title = it.titleFilter,
                                    ownerId = it.ownerId,
                                    adType = when(it.dealSide){
                                                MkplDealSide.DEMAND -> MkplDealSide.SUPPLY
                                                MkplDealSide.SUPPLY -> MkplDealSide.DEMAND
                                                MkplDealSide.NONE -> MkplDealSide.NONE
                                              },
                                )
                            ),
                        )
                    }
                )
            }
            val settigs by lazy { MkplSettings(repoTest = repo) }
            module()
            moduleJvm(settigs)
        }
        val client = myClient()

        val response = client.post("/v1/ad/offers") {
            val requestObj = AdOffersRequest(
                requestId = "12345",
                ad = AdReadObject(
                    id = "666",
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdOffersResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.ads?.size)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}
