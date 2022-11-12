package ru.otus.otuskotlin.marketplace.app.ktor.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenInfo(
    @field:JsonProperty("access_token") val accessToken: String? = null,
    @field:JsonProperty("expires_in") val expiresIn: Int? = null,
    @field:JsonProperty("refresh_expires_in") val refreshExpiresIn: Int? = null,
    @field:JsonProperty("refresh_token") val refreshToken: String? = null,
    @field:JsonProperty("token_type") val tokenType: String? = null,
    @field:JsonProperty("not-before-policy") val notBeforePolicy: Int? = null,
    @field:JsonProperty("session_state") val sessionState: String? = null,
    @field:JsonProperty("scope") val scope: String? = null,
)
