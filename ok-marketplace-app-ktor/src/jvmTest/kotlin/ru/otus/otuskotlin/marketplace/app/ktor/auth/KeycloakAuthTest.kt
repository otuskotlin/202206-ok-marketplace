package ru.otus.otuskotlin.marketplace.app.ktor.auth

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig
import ru.otus.otuskotlin.marketplace.app.ktor.module
import ru.otus.otuskotlin.marketplace.app.ktor.moduleJvm
import ru.otus.otuskotlin.marketplace.common.models.MkplSettings
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory
import kotlin.test.assertEquals

class KeycloakAuthTest {

    @Test
    fun keycloak() = testApplication {
        val authConfig = KtorAuthConfig.TEST
            .copy(
                certUrl = "${KeycloakSettings.host}/auth/realms/${KtorAuthConfig.TEST.realm}" +
                        "/protocol/openid-connect/certs",
                issuer = "${KeycloakSettings.host}/auth/realms/${KtorAuthConfig.TEST.realm}",
            )

        // Server settings
        val uuidNew = "eee"
        val settings = MkplSettings(
            repoTest = AdRepoInMemory(randomUuid = { uuidNew })
        )
        application {
            module(authConfig = authConfig, settings = settings)
            moduleJvm(authConfig = authConfig, settings = settings)
        }
        val client = authClient()

        val createAd = AdCreateObject(
            title = "Болт",
            description = "КРУТЕЙШИЙ",
            adType = DealSide.DEMAND,
            visibility = AdVisibility.PUBLIC,
        )

        val response = client.post("/v1/ad/create") {
            val requestObj = AdCreateRequest(
                requestId = "12345",
                ad = createAd,
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
//            addAuth(id = userId, config = KtorAuthConfig.TEST)
            setBody(requestObj)
        }
        val responseObj = response.body<AdCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidNew, responseObj.ad?.id)
        assertEquals(createAd.title, responseObj.ad?.title)
        assertEquals(createAd.description, responseObj.ad?.description)
        assertEquals(createAd.adType, responseObj.ad?.adType)
        assertEquals(createAd.visibility, responseObj.ad?.visibility)
    }

    object UserCred {
        const val user: String = "otus-test"
        const val pass: String = "otus-pass"
    }

    object KeycloakSettings {
        const val host: String = "http://localhost:8081"
        val authActionUrl =
            "$host/auth/realms/${KtorAuthConfig.TEST.realm}/protocol/openid-connect/token"
    }

    private var bearerToken: BearerTokens? = null
    private val authClient = HttpClient(CIO)

    private fun ApplicationTestBuilder.authClient() = createClient {
        install(ContentNegotiation) {
            jackson(ContentType.Application.Json) {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
        install(Auth) {
            bearer {
                loadTokens {
                    bearerToken
                }
                refreshTokens {
                    val resp = authClient.submitForm(
                        url = KeycloakSettings.authActionUrl,
                        formParameters = Parameters.build {
                            append("client_id", KtorAuthConfig.TEST.clientId)
                            if (bearerToken == null) {
                                println(" GOING PASS")
                                append("grant_type", "password")
                                append("username", UserCred.user)
                                append("password", UserCred.pass)
                            } else {
                                println(" GOING TOKEN")
                                append("grant_type", "refresh_token")
                                append("refresh_token", bearerToken?.refreshToken ?: "")
                            }
                        }
                    ) {
                        markAsRefreshTokenRequest()
                    }
                    println(resp.bodyAsText())
                    val om = ObjectMapper()
//                    Какой-то баз, не работает штатное преобразование
//                    val tokenInfo: TokenInfo = resp.body()
                    val tokenInfo: TokenInfo = om.readValue(resp.bodyAsText(), TokenInfo::class.java)
                    bearerToken = BearerTokens(
                        tokenInfo.accessToken ?: "",
                        tokenInfo.refreshToken ?: ""
                    )
                    bearerToken
                }
            }
        }
    }
}
