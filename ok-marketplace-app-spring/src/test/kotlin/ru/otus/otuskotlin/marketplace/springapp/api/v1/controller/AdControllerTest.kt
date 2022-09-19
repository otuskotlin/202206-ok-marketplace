package ru.otus.otuskotlin.marketplace.springapp.api.v1.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.springapp.config.ServiceConfig


@WebFluxTest(AdController::class)
@Import(ServiceConfig::class)
internal class AdControllerTest {
    @Autowired
    private lateinit var webClient: WebTestClient

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
    }
}
