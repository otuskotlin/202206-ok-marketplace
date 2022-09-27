package ru.otus.otuskotlin.marketplace.app.kafka

import ru.otus.otuskotlin.marketplace.api.v1.apiV1RequestDeserialize
import ru.otus.otuskotlin.marketplace.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.marketplace.api.v1.models.IRequest
import ru.otus.otuskotlin.marketplace.api.v1.models.IResponse
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportAd

class ConsumerStrategyV1 : ConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: MkplContext): String {
        val response: IResponse = source.toTransportAd()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: MkplContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}