package ru.otus.otuskotlin.marketplace

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun `root endpoint`() = testApplication {
        application(Application::module)

        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, world!", response.bodyAsText())
    }
}
