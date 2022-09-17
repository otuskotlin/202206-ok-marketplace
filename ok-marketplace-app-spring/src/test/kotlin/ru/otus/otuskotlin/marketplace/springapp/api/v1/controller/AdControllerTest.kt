package ru.otus.otuskotlin.marketplace.springapp.api.v1.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coVerify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor


@WebFluxTest(AdController::class)
internal class AdControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: MkplAdProcessor

    @Test
    fun createAd() {
        webClient.post()
            .uri("/v1/ad/create")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(AdCreateRequest(
                debug = AdDebug(mode = AdRequestDebugMode.STUB, stub = AdRequestDebugStubs.SUCCESS)
            )))
            .exchange()
            .expectStatus().isOk
            .expectBody(AdCreateResponse::class.java)
            .value {
                println("$it")
                assertThat(it.responseType).isEqualTo("create")
            }

        coVerify { processor.exec(any()) }
    }
}
