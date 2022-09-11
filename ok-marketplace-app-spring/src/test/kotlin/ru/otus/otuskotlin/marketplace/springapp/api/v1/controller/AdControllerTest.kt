package ru.otus.otuskotlin.marketplace.springapp.api.v1.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coVerify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.otuskotlin.marketplace.api.v1.models.AdCreateRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdCreateResponse
import ru.otus.otuskotlin.marketplace.api.v1.models.AdResponseObject
import ru.otus.otuskotlin.marketplace.api.v1.models.ResponseResult
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor


@WebMvcTest(AdController::class)
internal class AdControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: MkplAdProcessor

    @Test
    fun createAd() {
        val request = mapper.writeValueAsString(AdCreateRequest())
        val response = mapper.writeValueAsString(
            AdCreateResponse(
                responseType = "create",
                result = ResponseResult.ERROR,
                ad = AdResponseObject()
            )
        )

        mvc
            .perform(
                post("/v1/ad/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
            )
            .andExpect(status().isOk)
            .andExpect(content().json(response))

        coVerify { processor.exec(any()) }
    }
}