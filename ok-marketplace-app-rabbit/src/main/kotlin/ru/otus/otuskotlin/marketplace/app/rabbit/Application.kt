package ru.otus.otuskotlin.marketplace.app.rabbit

import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.marketplace.app.rabbit.controller.RabbitController
import ru.otus.otuskotlin.marketplace.app.rabbit.processor.RabbitDirectProcessor
import ru.otus.otuskotlin.marketplace.app.rabbit.processor.RabbitDirectProcessorV2
import ru.otus.otuskotlin.marketplace.backend.services.AdService

fun main() {
    val config = RabbitConfig()
    val service = AdService()

    val producerConfig = RabbitExchangeConfiguration(
        keyIn = "in-v1",
        keyOut = "out-v1",
        exchange = "transport-exchange",
        queue = "v1-queue",
        consumerTag = "v1-consumer",
        exchangeType = "direct"
    )

    val producerConfigV2 = RabbitExchangeConfiguration(
        keyIn = "in-v2",
        keyOut = "out-v2",
        exchange = "transport-exchange",
        queue = "v2-queue",
        consumerTag = "v2-consumer",
        exchangeType = "direct"
    )

    val processor by lazy {
        RabbitDirectProcessor(
            config = config,
            processorConfig = producerConfig,
            service = service
        )
    }

    val processor2 by lazy {
        RabbitDirectProcessorV2(
            config = config,
            processorConfig = producerConfigV2,
            service = service
        )
    }
    val controller by lazy {
        RabbitController(
            processors = setOf(processor, processor2)
        )
    }
    controller.start()
}
