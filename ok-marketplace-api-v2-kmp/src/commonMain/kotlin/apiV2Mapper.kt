package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.json.Json

val apiV2Mapper = Json {
    classDiscriminator = "requestType"
    encodeDefaults = true
}
