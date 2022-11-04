package ru.otus.otuskotlin.marketplace.app.ktor.auth

import io.ktor.client.request.*
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig

actual fun HttpRequestBuilder.addAuth(
    id: String,
    groups: List<String>,
    config: KtorAuthConfig,
) {
}
