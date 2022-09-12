package ru.otus.otuskotlin.marketplace.mappers.v2.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.otuskotlin.marketplace.api.v2.models.AdDeleteRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdOffersRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdReadRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdUpdateRequest
import ru.otus.otuskotlin.marketplace.common.helpers.errorMapping
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.mappers.v2.MapContext

internal fun ICorAddExecDsl<MapContext>.takeId(title: String) = worker {
    this.title = title
    on { state == MapContext.MapState.RUNNING }
    handle {
        when(val r = req) {
            is AdReadRequest -> adResult.id = MkplAdId(r.ad?.id ?: "")
            is AdUpdateRequest -> adResult.id = MkplAdId(r.ad?.id ?: "")
            is AdDeleteRequest -> adResult.id = MkplAdId(r.ad?.id ?: "")
            is AdOffersRequest -> adResult.id = MkplAdId(r.ad?.id ?: "")
            else -> errorMapping(
                field = "id",
                violationCode = "wrongRequest",
                description = "invalid request contains no required field"
            )
        }
    }
}
