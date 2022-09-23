package ru.otus.otuskotlin.marketplace.api.v2.responses

import ru.otus.otuskotlin.marketplace.api.v2.AdResponseSerializer
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse

fun apiV2ResponseSerialize(Response: IResponse): String = apiV2Mapper.encodeToString(AdResponseSerializer, Response)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2ResponseDeserialize(json: String): T = apiV2Mapper.decodeFromString(AdResponseSerializer, json) as T
