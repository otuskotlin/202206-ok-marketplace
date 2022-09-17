package ru.otus.otuskotlin.marketplace.springapp.api.v1.controller

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
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.api.v1.models.AdCreateRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdCreateResponse
import ru.otus.otuskotlin.marketplace.api.v1.models.AdResponseObject
import ru.otus.otuskotlin.marketplace.api.v1.models.ResponseResult
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor


@WebMvcTest(AdController::class)
internal class AdControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    private val mapper = apiV1Mapper

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: MkplAdProcessor

    @Test
    fun createAd() {
        val request = mapper.writeValueAsString(AdCreateRequest())
        val response = mapper.writeValueAsString(
            AdCreateResponse(
                result = ResponseResult.ERROR,
//                ad = AdResponseObject()
            )
        )

        println("RESP: $response")
        mvc
            .perform(
                post("/v1/ad/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
            )
            .andExpect(status().isOk)
            .andDo {
                println("RESSS: ${it.response.contentAsString}")
            }
            .andExpect(content().json(response, false))

        coVerify { processor.exec(any()) }
    }
}
