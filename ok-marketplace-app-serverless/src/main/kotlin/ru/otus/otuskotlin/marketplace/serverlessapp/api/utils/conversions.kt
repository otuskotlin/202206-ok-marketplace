package ru.otus.otuskotlin.marketplace.serverlessapp.api.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Request
import ru.otus.otuskotlin.marketplace.serverlessapp.api.model.Response
import java.util.*

val objectMapper: ObjectMapper = jacksonObjectMapper().findAndRegisterModules()

inline fun <reified T> Request.toTransportModel(): T =
    if (isBase64Encoded) {
        objectMapper.readValue(Base64.getDecoder().decode(body))
    } else {
        objectMapper.readValue(body)
    }

/**
 * V1
 */
fun ru.otus.otuskotlin.marketplace.api.v1.models.IResponse.toResponse(): Response =
    Response(
        200,
        mapOf("Content-Type" to "application/json"),
        objectMapper.writeValueAsString(this)
    )

/**
 * V2
 */
fun ru.otus.otuskotlin.marketplace.api.v2.models.IResponse.toResponse(): Response =
    Response(
        200,
        mapOf("Content-Type" to "application/json"),
        objectMapper.writeValueAsString(this)
    )
