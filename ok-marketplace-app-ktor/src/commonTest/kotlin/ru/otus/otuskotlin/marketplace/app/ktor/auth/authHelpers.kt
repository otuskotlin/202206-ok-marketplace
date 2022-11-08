package ru.otus.otuskotlin.marketplace.app.ktor.auth

import io.ktor.client.request.*
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig

expect fun HttpRequestBuilder.addAuth(
    id: String = "user1",
    groups: List<String> = listOf("USER", "TEST"),
    config: KtorAuthConfig = KtorAuthConfig.TEST,
)
