package ru.otus.otuskotlin.marketplace.api.v1

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import ru.otus.otuskotlin.marketplace.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.IResponse

val apiV1Mapper = ObjectMapper().apply {
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

fun apiV1RequestSerialize(request: IRequest): String = apiV1Mapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV1RequestDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IRequest::class.java) as T

fun apiV1ResponseSerialize(response: IResponse): String = apiV1Mapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponse> apiV1ResponseDeserialize(json: String): T =
    apiV1Mapper.readValue(json, IResponse::class.java) as T
