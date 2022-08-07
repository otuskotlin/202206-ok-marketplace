package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.otus.otuskotlin.marketplace.api.v2.models.*

@OptIn(ExperimentalSerializationApi::class)
val apiV2Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is AdCreateRequest ->  RequestSerializer(AdCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is AdReadRequest   ->  RequestSerializer(AdReadRequest  .serializer()) as SerializationStrategy<IRequest>
                is AdUpdateRequest ->  RequestSerializer(AdUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is AdDeleteRequest ->  RequestSerializer(AdDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is AdSearchRequest ->  RequestSerializer(AdSearchRequest.serializer()) as SerializationStrategy<IRequest>
                is AdOffersRequest ->  RequestSerializer(AdOffersRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefaultSerializer(IResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is AdCreateResponse ->  ResponseSerializer(AdCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is AdReadResponse   ->  ResponseSerializer(AdReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is AdUpdateResponse ->  ResponseSerializer(AdUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is AdDeleteResponse ->  ResponseSerializer(AdDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is AdSearchResponse ->  ResponseSerializer(AdSearchResponse.serializer()) as SerializationStrategy<IResponse>
                is AdOffersResponse ->  ResponseSerializer(AdOffersResponse.serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }

        contextual(AdRequestSerializer)
        contextual(AdResponseSerializer)
    }
}
