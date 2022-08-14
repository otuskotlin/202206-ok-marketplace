package ru.otus.otuskotlin.markeplace.springapp.api.v1.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.otuskotlin.marketplace.api.v1.models.AdCreateRequest
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportCreate
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub


@WebMvcTest(AdController::class)
internal class AdControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createAd() {
        val request = mapper.writeValueAsString(AdCreateRequest())
        val response = mapper.writeValueAsString(
            MkplContext().apply { adResponse = MkplAdStub.get() }.toTransportCreate()
        )

        mvc
            .perform(
                post("/v1/ad/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
            )
            .andExpect(status().isOk)
            .andExpect(content().json(response))
    }
}