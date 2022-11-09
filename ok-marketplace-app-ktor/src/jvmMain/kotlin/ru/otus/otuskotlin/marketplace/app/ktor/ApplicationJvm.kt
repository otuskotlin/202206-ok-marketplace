package ru.otus.otuskotlin.marketplace.app.ktor

import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorWsSessions
import ru.otus.otuskotlin.marketplace.app.ktor.v1.mpWsHandlerV1
import ru.otus.otuskotlin.marketplace.app.ktor.v1.v1Ad
import ru.otus.otuskotlin.marketplace.app.ktor.v1.v1Offer
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplSettings
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory
import java.net.URL
import java.security.interfaces.RSAPublicKey

fun Application.moduleJvm(
    settings: MkplSettings? = null,
    authConfig: KtorAuthConfig = KtorAuthConfig(environment),
) {
    install(ContentNegotiation) {
        jackson {
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }
    install(CallLogging) {
        level = Level.INFO
    }
    install(Authentication) {
        jwt("auth-jwt") {
            realm = authConfig.realm

            verifier {
                val token = it.render().replace(it.authScheme, "").trim()
                val x = JWT.decode(token)
                val keyId = x.keyId
                val provider =
                    UrlJwkProvider(URL("http://localhost:8081/auth/realms/${KtorAuthConfig.TEST.realm}/protocol/openid-connect/certs"))
                val jwk = provider.get(keyId)
                val publicKey = jwk.publicKey
                if (publicKey !is RSAPublicKey) {
                    throw IllegalArgumentException("Key with ID was found in JWKS but is not a RSA-key.")
                }
                val algorithm = Algorithm.RSA256(publicKey, null)

                JWT
                    .require(algorithm)
//                    .withAudience(authConfig.audience)
//                    .withIssuer(authConfig.issuer)
                    .build()
            }
            validate { jwtCredential: JWTCredential ->
//                JWTPrincipal(jwtCredential.payload)
                when {
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        this@moduleJvm.log.error("Groups claim must not be empty in JWT token")
                        null
                    }

                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }
    val corSettings by lazy {
        settings ?: MkplSettings(
            repoTest = AdRepoInMemory()
        )
    }
    val processor = MkplAdProcessor(settings = corSettings)
    routing {
        authenticate("auth-jwt") {
            route("v1") {
                v1Ad(processor)
                v1Offer(processor)
            }
        }
        webSocket("/ws/v1") {
            mpWsHandlerV1(processor, KtorWsSessions.sessions)
        }
    }
}
