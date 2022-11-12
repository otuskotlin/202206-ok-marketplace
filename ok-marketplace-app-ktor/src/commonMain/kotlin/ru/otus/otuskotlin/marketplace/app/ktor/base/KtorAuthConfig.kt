package ru.otus.otuskotlin.marketplace.app.ktor.base

import io.ktor.server.application.*

data class KtorAuthConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val clientId: String,
    val certUrl: String? = null,
) {
    constructor(environment: ApplicationEnvironment): this(
        secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "",
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        realm = environment.config.property("jwt.realm").getString(),
        clientId = environment.config.property("jwt.clientId").getString(),
        certUrl = environment.config.propertyOrNull("jwt.certUrl")?.getString(),
    )

    companion object {
        const val ID_CLAIM = "sub"
        const val GROUPS_CLAIM = "groups"
        const val F_NAME_CLAIM = "fname"
        const val M_NAME_CLAIM = "mname"
        const val L_NAME_CLAIM = "lname"

        val TEST = KtorAuthConfig(
                secret = "secret",
                issuer = "OtusKotlin",
                audience = "ad-users",
                realm = "otus-marketplace",
                clientId = "otus-marketplace-service",
            )
    }
}
