package ru.otus.otuskotlin.marketplace.api.v2.requests

import ru.otus.otuskotlin.marketplace.api.v2.IApiStrategy
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest

sealed interface IRequestStrategy: IApiStrategy<IRequest> {
    companion object {
        private val members: List<IRequestStrategy> = listOf<IRequestStrategy>(
            CreateRequestStrategy,
            ReadRequestStrategy,
            UpdateRequestStrategy,
            DeleteRequestStrategy,
            SearchRequestStrategy,
            OffersRequestStrategy,
        )
        val membersByDiscriminator = members.associateBy { it.discriminator }
        val membersByClazz = members.associateBy { it.clazz }
    }
}
