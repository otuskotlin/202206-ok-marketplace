package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ru.otus.otuskotlin.marketplace.api.v2.models.*


val AdResponseSerializer1 = ResponseSerializer(AdResponseSerializer)

internal object AdResponseSerializer : JsonContentPolymorphicSerializer<IResponse>(IResponse::class) {
    private const val discriminator = "responseType"

    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out IResponse> {

        return when (val discriminatorValue = element.jsonObject[discriminator]?.jsonPrimitive?.content) {
            "create" -> AdCreateResponse.serializer()
            "read" -> AdReadResponse.serializer()
            "update" -> AdUpdateResponse.serializer()
            "delete" -> AdDeleteResponse.serializer()
            "search" -> AdSearchResponse.serializer()
            "offers" -> AdOffersResponse.serializer()
            else -> throw SerializationException(
                "Unknown value '${discriminatorValue}' in discriminator '${discriminator}' " +
                        "property of ${IResponse::class} implementation"
            )
        }
    }

}

class ResponseSerializer<T: IResponse>(private val serializer: KSerializer<T>): KSerializer<T> by serializer {
    override fun serialize(encoder: Encoder, value: T) {
        val response = when(value) {
            is AdCreateResponse -> value.copy(responseType = "create")
            is AdReadResponse   -> value.copy(responseType = "read")
            is AdUpdateResponse -> value.copy(responseType = "update")
            is AdDeleteResponse -> value.copy(responseType = "delete")
            is AdSearchResponse -> value.copy(responseType = "search")
            is AdOffersResponse -> value.copy(responseType = "offers")
            else -> throw SerializationException(
                "Unknown class to serialize as IResponse instance in ResponseSerializer"
            )
        }
        @Suppress("UNCHECKED_CAST")
        return serializer.serialize(encoder, response as T)
    }
}
