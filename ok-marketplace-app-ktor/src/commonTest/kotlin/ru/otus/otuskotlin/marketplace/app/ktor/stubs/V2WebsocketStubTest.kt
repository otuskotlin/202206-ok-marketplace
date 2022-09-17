package ru.otus.otuskotlin.marketplace.app.ktor.stubs

import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.*
import ru.otus.otuskotlin.marketplace.app.ktor.module
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class V2WebsocketStubTest {

    @Test
    fun create() {
        testApplication {
            application {
                module()
            }

            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v2") {
                    withTimeout(3000) {
                        val incame = incoming.receive()
                        val response = apiV2Mapper.decodeFromString((incame as Frame.Text).readText()) as IResponse
                        assertIs<AdInitResponse>(response)
                    }
                val requestObj = AdCreateRequest(
                    requestType = "create",
                    requestId = "12345",
                    ad = AdCreateObject(
                        title = "Болт",
                        description = "КРУТЕЙШИЙ",
                        adType = DealSide.DEMAND,
                        visibility = AdVisibility.PUBLIC,
                    ),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStubs.SUCCESS
                    )
                )
                    send(Frame.Text(apiV2Mapper.encodeToString(requestObj)))
                    withTimeout(3000) {
                        val incame = incoming.receive()
                        val response = apiV2Mapper.decodeFromString((incame as Frame.Text).readText()) as AdCreateResponse

                        assertEquals("666", response.ad?.id)
                    }
            }
        }
    }

    @Test
    fun read() {
        testApplication {
            application {
                module()
            }

            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v2") {
                    withTimeout(3000) {
                        incoming.receive().readBytes()
                    }
                val requestObj = AdReadRequest(
                    requestType = "read",
                    requestId = "12345",
                    ad = AdReadObject("666"),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStubs.SUCCESS
                    )
                )
                    send(Frame.Text(apiV2Mapper.encodeToString(requestObj)))
                    withTimeout(3000) {
                        val incame = incoming.receive()
                        val response = apiV2Mapper.decodeFromString((incame as Frame.Text).readText()) as AdReadResponse

                        assertEquals("666", response.ad?.id)
                    }
            }
        }
    }

    @Test
    fun update() {
        testApplication {
            application {
                module()
            }

            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v2") {
                    withTimeout(3000) {
                        incoming.receive()
                    }
                val requestObj = AdUpdateRequest(
                    requestType = "update",
                    requestId = "12345",
                    ad = AdUpdateObject(
                        id = "666",
                        title = "Болт",
                        description = "КРУТЕЙШИЙ",
                        adType = DealSide.DEMAND,
                        visibility = AdVisibility.PUBLIC,
                    ),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStubs.SUCCESS
                    )
                )
                    send(Frame.Text(apiV2Mapper.encodeToString(requestObj)))
                    withTimeout(3000) {
                        val incame = incoming.receive()
                        val response = apiV2Mapper.decodeFromString((incame as Frame.Text).readText()) as AdUpdateRequest

                        assertEquals("666", response.ad?.id)
                    }
            }
        }
    }

    @Test
    fun delete() {
        testApplication {
            application {
                module()
            }

            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v2") {
                    withTimeout(3000) {
                        incoming.receive()
                    }
                val requestObj = AdDeleteRequest(
                    requestType = "delete",
                    requestId = "12345",
                    ad = AdDeleteObject(
                        id = "666",
                    ),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStubs.SUCCESS
                    )
                )
                    send(Frame.Text(apiV2Mapper.encodeToString(requestObj)))
                    withTimeout(3000) {
                        val incame = incoming.receive()
                        val response = apiV2Mapper.decodeFromString((incame as Frame.Text).readText()) as AdDeleteResponse

                        assertEquals("666", response.ad?.id)
                    }
            }
        }
    }

    @Test
    fun search() {
        testApplication {
            application {
                module()
            }

            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v2") {
                    withTimeout(3000) {
                        incoming.receive()
                    }
                val requestObj = AdSearchRequest(
                    requestType = "search",
                    requestId = "12345",
                    adFilter = AdSearchFilter(),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStubs.SUCCESS
                    )
                )
                    send(Frame.Text(apiV2Mapper.encodeToString(requestObj)))
                    withTimeout(3000) {
                        val incame = incoming.receive()
                        val response = apiV2Mapper.decodeFromString((incame as Frame.Text).readText()) as AdSearchResponse

                        assertEquals("d-666-01", response.ads?.first()?.id)
                    }
            }
        }
    }

    @Test
    fun offers() {
        testApplication {
            application {
                module()
            }

            val client = createClient {
                install(WebSockets)
            }

            client.webSocket("/ws/v2") {
                    withTimeout(3000) {
                        incoming.receive()
                    }
                val requestObj = AdOffersRequest(
                    requestType = "offers",
                    requestId = "12345",
                    ad = AdReadObject(
                        id = "666",
                    ),
                    debug = AdDebug(
                        mode = AdRequestDebugMode.STUB,
                        stub = AdRequestDebugStubs.SUCCESS
                    )
                )
                    send(Frame.Text(apiV2Mapper.encodeToString(requestObj)))
                    withTimeout(3000) {
                        val incame = incoming.receive()
                        val response = apiV2Mapper.decodeFromString((incame as Frame.Text).readText()) as AdOffersResponse

                        assertEquals("s-666-01", response.ads?.first()?.id)
                    }
            }
        }
    }
}
