package ru.otus.otuskotlin.marketplace.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.marketplace.api.v2.models.AdDeleteResponse
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse
import kotlin.reflect.KClass

object DeleteResponseStrategy: IResponseStrategy {
    override val discriminator: String = "delete"
    override val clazz: KClass<out IResponse> = AdDeleteResponse::class
    override val serializer: KSerializer<out IResponse> = AdDeleteResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is AdDeleteResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}
