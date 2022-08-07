package ru.otus.otuskotlin.marketplace.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.marketplace.api.v2.models.AdSearchRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import kotlin.reflect.KClass

object SearchRequestStrategy: IRequestStrategy {
    override val discriminator: String = "search"
    override val clazz: KClass<out IRequest> = AdSearchRequest::class
    override val serializer: KSerializer<out IRequest> = AdSearchRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is AdSearchRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}
