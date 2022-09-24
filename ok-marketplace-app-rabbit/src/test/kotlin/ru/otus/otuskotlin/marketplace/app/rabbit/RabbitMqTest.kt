package ru.otus.otuskotlin.marketplace.app.rabbit

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.testcontainers.containers.RabbitMQContainer
import ru.otus.otuskotlin.marketplace.api.v1.models.AdCreateObject
import ru.otus.otuskotlin.marketplace.api.v1.models.AdCreateRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.AdCreateResponse
import ru.otus.otuskotlin.marketplace.api.v1.models.AdDebug
import ru.otus.otuskotlin.marketplace.api.v1.models.AdRequestDebugMode
import ru.otus.otuskotlin.marketplace.api.v1.models.AdRequestDebugStubs
import ru.otus.otuskotlin.marketplace.api.v2.requests.apiV2RequestSerialize
import ru.otus.otuskotlin.marketplace.api.v2.responses.apiV2ResponseDeserialize
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.marketplace.app.rabbit.controller.RabbitController
import ru.otus.otuskotlin.marketplace.app.rabbit.processor.RabbitDirectProcessor
import ru.otus.otuskotlin.marketplace.app.rabbit.processor.RabbitDirectProcessorV2
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import ru.otus.otuskotlin.marketplace.api.v2.models.AdCreateObject as AdCreateObjectV2
import ru.otus.otuskotlin.marketplace.api.v2.models.AdCreateRequest as AdCreateRequestV2
import ru.otus.otuskotlin.marketplace.api.v2.models.AdCreateResponse as AdCreateResponseV2
import ru.otus.otuskotlin.marketplace.api.v2.models.AdDebug as AdDebugV2
import ru.otus.otuskotlin.marketplace.api.v2.models.AdRequestDebugMode as AdRequestDebugModeV2
import ru.otus.otuskotlin.marketplace.api.v2.models.AdRequestDebugStubs as AdRequestDebugStubsV2

internal class RabbitMqTest {

    companion object {
        const val exchange = "test-exchange"
        const val exchangeType = "direct"
    }

    val container by lazy {
//            Этот образ предназначен для дебагинга, он содержит панель управления на порту httpPort
//            RabbitMQContainer("rabbitmq:3-management").apply {
//            Этот образ минимальный и не содержит панель управления
        RabbitMQContainer("rabbitmq:latest").apply {
            withExposedPorts(5672, 15672)
            withUser("guest", "guest")
            start()
        }
    }

    val rabbitMqTestPort: Int by lazy {
        container.getMappedPort(5672)
    }
    val config by lazy {
        RabbitConfig(
            port = rabbitMqTestPort
        )
    }
    val processor by lazy {
        RabbitDirectProcessor(
            config = config,
            processorConfig = RabbitExchangeConfiguration(
                keyIn = "in-v1",
                keyOut = "out-v1",
                exchange = exchange,
                queue = "v1-queue",
                consumerTag = "test-tag",
                exchangeType = exchangeType
            )
        )
    }
    val processorV2 by lazy {
        RabbitDirectProcessorV2(
            config = config,
            processorConfig = RabbitExchangeConfiguration(
                keyIn = "in-v2",
                keyOut = "out-v2",
                exchange = exchange,
                queue = "v2-queue",
                consumerTag = "v2-consumer",
                exchangeType = exchangeType
            )
        )
    }
    val controller by lazy {
        RabbitController(
            processors = setOf(processor, processorV2)
        )
    }
    val mapper = ObjectMapper()

    @BeforeTest
    fun tearUp() {
        controller.start()
    }

    @Test
    fun adCreateTest() {
        val keyOut = processor.processorConfig.keyOut
        val keyIn = processor.processorConfig.keyIn
        ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, mapper.writeValueAsBytes(boltCreateV1))

                runBlocking {
                    withTimeoutOrNull(265L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = mapper.readValue(responseJson, AdCreateResponse::class.java)
                val expected = MkplAdStub.get()

                assertEquals(expected.title, response.ad?.title)
                assertEquals(expected.description, response.ad?.description)
            }
        }
    }

    @Test
    fun adCreateTestV2() {
        val keyOut = processorV2.processorConfig.keyOut
        val keyIn = processorV2.processorConfig.keyIn
        ConnectionFactory().apply {
            host = config.host
            port = config.port
            username = "guest"
            password = "guest"
        }.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                var responseJson = ""
                channel.exchangeDeclare(exchange, "direct")
                val queueOut = channel.queueDeclare().queue
                channel.queueBind(queueOut, exchange, keyOut)
                val deliverCallback = DeliverCallback { consumerTag, delivery ->
                    responseJson = String(delivery.body, Charsets.UTF_8)
                    println(" [x] Received by $consumerTag: '$responseJson'")
                }
                channel.basicConsume(queueOut, true, deliverCallback, CancelCallback { })

                channel.basicPublish(exchange, keyIn, null, apiV2RequestSerialize(boltCreateV2).toByteArray())

                runBlocking {
                    withTimeoutOrNull(265L) {
                        while (responseJson.isBlank()) {
                            delay(10)
                        }
                    }
                }

                println("RESPONSE: $responseJson")
                val response = apiV2ResponseDeserialize<AdCreateResponseV2>(responseJson)
                val expected = MkplAdStub.get()
                assertEquals(expected.title, response.ad?.title)
                assertEquals(expected.description, response.ad?.description)
            }
        }
    }

    private val boltCreateV1 = with(MkplAdStub.get()) {
        AdCreateRequest(
            ad = AdCreateObject(
                title = title,
                description = description
            ),
            requestType = "create",
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )
    }

    private val boltCreateV2 = with(MkplAdStub.get()) {
        AdCreateRequestV2(
            ad = AdCreateObjectV2(
                title = title,
                description = description
            ),
            requestType = "create",
            debug = AdDebugV2(
                mode = AdRequestDebugModeV2.STUB,
                stub = AdRequestDebugStubsV2.SUCCESS
            )
        )
    }
}
