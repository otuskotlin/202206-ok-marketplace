package ru.otus.otuskotlin.marketplace.app.rabbit.processor

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.app.rabbit.RabbitProcessorBase
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.marketplace.backend.services.AdService
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.addError
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportAd

class RabbitDirectProcessor(
    config: RabbitConfig,
    processorConfig: RabbitExchangeConfiguration,
    private val service: AdService,
) : RabbitProcessorBase(config, processorConfig) {

    private val context = MkplContext()

    override suspend fun Channel.processMessage(message: Delivery) {
        context.apply {
            timeStart = Clock.System.now()
        }

        jacksonMapper.readValue(message.body, IRequest::class.java).run {
            context.fromTransport(this).also {
                println("TYPE: ${this::class.simpleName}")
            }
        }
        val response = service.exec(context).run { context.toTransportAd() }
        jacksonMapper.writeValueAsBytes(response).also {
            println("Publishing $response to ${processorConfig.exchange} exchange for keyOut ${processorConfig.keyOut}")
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }.also {
            println("published")
        }
    }

    override fun Channel.onError(e: Throwable) {
        e.printStackTrace()
        context.state = MkplState.FAILING
        context.addError(error = arrayOf(e.asMkplError()))
        val response = context.toTransportAd()
        jacksonMapper.writeValueAsBytes(response).also {
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it)
        }
    }
}
