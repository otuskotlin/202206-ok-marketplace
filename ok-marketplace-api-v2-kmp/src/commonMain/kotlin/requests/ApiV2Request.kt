package ru.otus.otuskotlin.marketplace.api.v2.requests

import ru.otus.otuskotlin.marketplace.api.v2.AdRequestSerializer
import ru.otus.otuskotlin.marketplace.api.v2.apiV2Mapper
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest

fun apiV2RequestSerialize(request: IRequest): String = apiV2Mapper.encodeToString(AdRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : Any> apiV2RequestDeserialize(json: String): T = apiV2Mapper.decodeFromString(AdRequestSerializer, json) as T
