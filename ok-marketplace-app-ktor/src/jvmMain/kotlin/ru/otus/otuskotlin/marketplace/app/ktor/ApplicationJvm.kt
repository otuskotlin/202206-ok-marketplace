package ru.otus.otuskotlin.marketplace.app.ktor

import com.auth0.jwt.JWT
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.cio.*
import io.ktor.server.config.yaml.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.otus.otuskotlin.marketplace.api.v1.apiV1Mapper
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorWsSessions
import ru.otus.otuskotlin.marketplace.app.ktor.base.resolveAlgorithm
import ru.otus.otuskotlin.marketplace.app.ktor.v1.mpWsHandlerV1
import ru.otus.otuskotlin.marketplace.app.ktor.v1.v1Ad
import ru.otus.otuskotlin.marketplace.app.ktor.v1.v1Offer
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.models.MkplSettings
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory

@Suppress("unused") // Referenced in application.conf
fun main() {
    embeddedServer(CIO, environment = applicationEngineEnvironment {
        val conf = YamlConfigLoader().load("./application.yaml")
            ?: throw RuntimeException("Cannot read application.yaml")
//        println(conf)
        config = conf
//        println("File read")

//        module {
//            module()
//            moduleJvm()
//        }
        connector {
            port =  8080
            host =  "0.0.0.0"
        }
    }).apply {
//        addShutdownHook {
//            println("Stop is requested")
//            stop(3000, 5000)
//            println("Exiting")
//        }
        start(true)
    }
}

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
                val algorithm = it.resolveAlgorithm(authConfig)
                JWT
                    .require(algorithm)
                    .withAudience(authConfig.audience)
                    .withIssuer(authConfig.issuer)
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
