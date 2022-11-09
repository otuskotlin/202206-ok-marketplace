package ru.otus.otuskotlin.marketplace.app.ktor.auth

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    @SerialName("access_token") @field:JsonProperty("access_token") val accessToken: String? = null,
    @SerialName("expires_in") @field:JsonProperty("expires_in") val expiresIn: Int? = null,
    @SerialName("refresh_expires_in") @field:JsonProperty("refresh_expires_in") val refreshExpiresIn: Int? = null,
    @SerialName("refresh_token") @field:JsonProperty("refresh_token") val refreshToken: String? = null,
    @SerialName("token_type") @field:JsonProperty("token_type") val tokenType: String? = null,
    @SerialName("not-before-policy") @field:JsonProperty("not-before-policy") val notBeforePolicy: Int? = null,
    @SerialName("session_state") @field:JsonProperty("session_state") val sessionState: String? = null,
    @SerialName("scope") @field:JsonProperty("scope") val scope: String? = null,
)
