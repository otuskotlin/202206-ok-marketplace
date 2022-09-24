package ru.otus.otuskotlin.marketplace.app.rabbit.processor

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Delivery
import kotlinx.datetime.Clock
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v2.requests.apiV2RequestDeserialize
import ru.otus.otuskotlin.marketplace.api.v2.responses.apiV2ResponseSerialize
import ru.otus.otuskotlin.marketplace.app.rabbit.RabbitProcessorBase
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitConfig
import ru.otus.otuskotlin.marketplace.app.rabbit.config.RabbitExchangeConfiguration
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.helpers.addError
import ru.otus.otuskotlin.marketplace.common.helpers.asMkplError
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.mappers.v2.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v2.toTransportAd

class RabbitDirectProcessorV2(
    config: RabbitConfig,
    processorConfig: RabbitExchangeConfiguration,
    private val processor: MkplAdProcessor = MkplAdProcessor(),
) : RabbitProcessorBase(config, processorConfig) {

    private val context = MkplContext()

    override suspend fun Channel.processMessage(message: Delivery) {
        context.apply {
            timeStart = Clock.System.now()
        }

        apiV2RequestDeserialize<IRequest>(String(message.body)).also {
            println("TYPE: ${it::class.java.simpleName}")
            context.fromTransport(it)
        }

        val response = processor.exec(context).run { context.toTransportAd() }
        apiV2ResponseSerialize(response).also {
            println("Publishing $response to ${processorConfig.exchange} exchange for keyOut ${processorConfig.keyOut}")
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it.toByteArray())
        }.also {
            println("published")
        }
    }

    override fun Channel.onError(e: Throwable) {
        e.printStackTrace()
        context.state = MkplState.FAILING
        context.addError(error = arrayOf(e.asMkplError()))
        val response = context.toTransportAd()
        apiV2ResponseSerialize(response).also {
            basicPublish(processorConfig.exchange, processorConfig.keyOut, null, it.toByteArray())
        }
    }
}
