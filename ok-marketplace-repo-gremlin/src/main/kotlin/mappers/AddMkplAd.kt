package ru.otus.otuskotlin.marketplace.backend.repository.gremlin.mappers

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as gr
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_AD_TYPE
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_DESCRIPTION
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_ID
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_LOCK
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_OWNER_ID
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_PRODUCT_ID
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_TITLE
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_TMP_RESULT
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.FIELD_VISIBILITY
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.AdGremlinConst.RESULT_SUCCESS
import ru.otus.otuskotlin.marketplace.backend.repository.gremlin.exceptions.WrongEnumException
import ru.otus.otuskotlin.marketplace.common.models.*

fun GraphTraversal<Vertex, Vertex>.addMkplAd(ad: MkplAd): GraphTraversal<Vertex, Vertex> =
    this
        .property(VertexProperty.Cardinality.single, FIELD_TITLE, ad.title.takeIf { it.isNotBlank() })
        .property(VertexProperty.Cardinality.single, FIELD_DESCRIPTION, ad.description.takeIf { it.isNotBlank() })
        .property(VertexProperty.Cardinality.single, FIELD_LOCK, ad.lock.takeIf { it != MkplAdLock.NONE }?.asString())
        .property(
            VertexProperty.Cardinality.single,
            FIELD_OWNER_ID,
            ad.ownerId.asString().takeIf { it.isNotBlank() }) // здесь можно сделать ссылку на объект владельца
        .property(VertexProperty.Cardinality.single, FIELD_AD_TYPE, ad.adType.takeIf { it != MkplDealSide.NONE }?.name)
        .property(
            VertexProperty.Cardinality.single,
            FIELD_VISIBILITY,
            ad.visibility.takeIf { it != MkplVisibility.NONE }?.name
        )
        .property(
            VertexProperty.Cardinality.single,
            FIELD_PRODUCT_ID,
            ad.productId.takeIf { it != MkplProductId.NONE }?.asString()
        )

fun GraphTraversal<Vertex, Vertex>.listMkplAd(result: String = RESULT_SUCCESS): GraphTraversal<Vertex, MutableMap<String, Any>> =
    project<Any?>(
        FIELD_ID,
        FIELD_OWNER_ID,
        FIELD_LOCK,
        FIELD_TITLE,
        FIELD_DESCRIPTION,
        FIELD_AD_TYPE,
        FIELD_VISIBILITY,
        FIELD_PRODUCT_ID,
        FIELD_TMP_RESULT,
    )
        .by(gr.id<Vertex>())
        .by(FIELD_OWNER_ID)
//        .by(gr.inE("Owns").outV().id())
        .by(FIELD_LOCK)
        .by(FIELD_TITLE)
        .by(FIELD_DESCRIPTION)
        .by(FIELD_AD_TYPE)
        .by(FIELD_VISIBILITY)
        .by(FIELD_PRODUCT_ID)
        .by(gr.constant(result))
//        .by(elementMap<Vertex, Map<Any?, Any?>>())

fun Map<String, Any?>.toMkplAd(): MkplAd = MkplAd(
    id = (this[FIELD_ID] as? String)?.let { MkplAdId(it) } ?: MkplAdId.NONE,
    ownerId = (this[FIELD_OWNER_ID] as? String)?.let { MkplUserId(it) } ?: MkplUserId.NONE,
    lock = (this[FIELD_LOCK] as? String)?.let { MkplAdLock(it) } ?: MkplAdLock.NONE,
    title = (this[FIELD_TITLE] as? String) ?: "",
    description = (this[FIELD_DESCRIPTION] as? String) ?: "",
    adType = when (val value = this[FIELD_AD_TYPE] as? String) {
        MkplDealSide.SUPPLY.name -> MkplDealSide.SUPPLY
        MkplDealSide.DEMAND.name -> MkplDealSide.DEMAND
        null -> MkplDealSide.NONE
        else -> throw WrongEnumException(
            "Cannot convert object from DB. " +
                    "adType = '$value' cannot be converted to ${MkplDealSide::class}"
        )
    },
    visibility = when (val value = this[FIELD_VISIBILITY]) {
        MkplVisibility.VISIBLE_PUBLIC.name -> MkplVisibility.VISIBLE_PUBLIC
        MkplVisibility.VISIBLE_TO_GROUP.name -> MkplVisibility.VISIBLE_TO_GROUP
        MkplVisibility.VISIBLE_TO_OWNER.name -> MkplVisibility.VISIBLE_TO_OWNER
        null -> MkplVisibility.NONE
        else -> throw WrongEnumException(
            "Cannot convert object from DB. " +
                    "visibility = '$value' cannot be converted to ${MkplVisibility::class}"
        )
    },
    productId = (this[FIELD_PRODUCT_ID] as? String)?.let { MkplProductId(it) } ?: MkplProductId.NONE,
)
