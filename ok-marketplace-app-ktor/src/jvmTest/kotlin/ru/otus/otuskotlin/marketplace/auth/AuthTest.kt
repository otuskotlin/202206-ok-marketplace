package ru.otus.otuskotlin.marketplace.auth

import io.ktor.client.request.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.otus.otuskotlin.marketplace.app.ktor.auth.addAuth
import ru.otus.otuskotlin.marketplace.app.ktor.base.KtorAuthConfig
import ru.otus.otuskotlin.marketplace.app.ktor.module
import ru.otus.otuskotlin.marketplace.app.ktor.moduleJvm
import kotlin.test.assertEquals

class AuthTest {
    @Test
    fun invalidAudience() = testApplication {
        application {
            module(authConfig = KtorAuthConfig.TEST)
            moduleJvm(authConfig = KtorAuthConfig.TEST)
        }

        val response = client.post("/v1/ad/create") {
            addAuth(config = KtorAuthConfig.TEST.copy(audience = "invalid"))
        }
        assertEquals(401, response.status.value)
    }
}
