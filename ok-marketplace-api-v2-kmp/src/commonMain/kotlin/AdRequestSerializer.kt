package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.otus.otuskotlin.marketplace.api.v2.models.*

internal object AdRequestSerializer : JsonContentPolymorphicSerializer<IRequest>(IRequest::class) {
    private const val discriminator = "requestType"
//    @OptIn(InternalSerializationApi::class)
//    override val descriptor: SerialDescriptor
//        get() = super.descriptor

    override fun selectDeserializer(element: JsonElement): KSerializer<out IRequest> {
//        println("ELEMENT: ${element.jsonObject["ad"]?.jsonObject?.get("props")}")
        return when (val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content) {
            "create" -> RequestSerializers.create
            "read" -> RequestSerializers.read
            "update" -> RequestSerializers.update
            "delete" -> RequestSerializers.delete
            "search" -> RequestSerializers.search
            "offers" -> RequestSerializers.offers
            else -> throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '$discriminator' " +
                        "property of IRequest implementation"
            )
        }
    }
}

internal object RequestSerializers {
    val create = RequestSerializer(AdCreateRequest.serializer())
    val read   = RequestSerializer(AdReadRequest.serializer())
    val update = RequestSerializer(AdUpdateRequest.serializer())
    val delete = RequestSerializer(AdDeleteRequest.serializer())
    val search = RequestSerializer(AdSearchRequest.serializer())
    val offers = RequestSerializer(AdOffersRequest.serializer())
}

internal class RequestSerializer<T: IRequest>(private val serializer: KSerializer<T>): KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) {
        val request = when(value) {
            is AdCreateRequest ->
                value.copy(requestType = "create")
            is AdReadRequest   -> value.copy(requestType = "read")
            is AdUpdateRequest -> value.copy(requestType = "update")
            is AdDeleteRequest -> value.copy(requestType = "delete")
            is AdSearchRequest -> value.copy(requestType = "search")
            is AdOffersRequest -> value.copy(requestType = "offers")
            else -> throw SerializationException(
                "Unknown class to serialize as IRequest instance in RequestSerializer"
            )
        }
        @Suppress("UNCHECKED_CAST")
        return serializer.serialize(encoder, request as T)
    }
//    override fun deserialize(decoder: Decoder): T {
//
//        TODO("Not yet implemented")
//    }
}
