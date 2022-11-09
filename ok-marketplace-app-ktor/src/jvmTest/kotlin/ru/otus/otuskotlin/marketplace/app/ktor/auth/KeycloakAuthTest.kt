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
        val authConfig = KtorAuthConfig.TEST.copy(secret = "MIICvzCCAacCBgGEWPQnCDANBgkqhkiG9w0BAQsFADAjMSEwHwYDVQQDDBhvdHVzLW1hcmtldHBsYWNlLXNlcnZpY2UwHhcNMjIxMTA4MjAzMzI2WhcNMzIxMTA4MjAzNTA2WjAjMSEwHwYDVQQDDBhvdHVzLW1hcmtldHBsYWNlLXNlcnZpY2UwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC0h/Zr2DTJW5jzGyoiJZMjHr03bNx+MiDPQaDdrWnhXWhqBkLX4VVcC3Ec9yQyN+rkG5lDt5wmxO8HAozvIpHQwCyzWy83EDatsUWKNioviJ5ISgdIfYoyIdIqU5M29VDH14t1loGfm7Dte6sM27d176t2GQfZj3ZW8BH8uRjBf0Vx0K0D5ICPb3OhO67fFBCdHUS+LvQDX+OTqAUlJYpMINiA4APNmqBLT7c5SA6+cHrWW/DXxVD37JmvbJMrgdCeAhnXhrEuq0FklCzOSCkd+0fhdj5v8ZdMSEpvxMAgmxYXT7g0mhJmL9zUFcufF6N5xemfUriSsqoNDry/v58lAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAC29uWUAulgvQi3iP/olMXnUFi0dhJiRmLfMgXPVPC35w8LP+BD3xqj1RJlD0G8I5l8+GwAfjFHoTJmGNH8/KrLIErA0CIwyQoYDbp9O8n9F8TgMb/JDoCuNrrTpA+xOP6BDmlqAMLZc0LSZ+MnuxnESy9iec6uwKNTwaNXwN5ZT7HsQ98U0moy6mnuwIRJovJyQ6zPSteH8J2AoxA5uUu9R3R9rd2dAzPZARU9u1C7U+eXqkEUFl/eLFCmZibm9lljryaQ/Xi0Dm0PEX9E9IS+nAvBr3DIj+yaeimlH17y3tqbrwY90Ol+zgvBuaAe63yX8nQRnpiUIqPQqbAIvyT8=")

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
        const val id: String = "6388dab0-b8cd-495b-89d4-4fbf4b7a1912"
    }

    object KeycloakSettings {
        private const val host: String = "http://localhost:8081"
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
