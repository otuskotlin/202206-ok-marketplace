package ru.otus.otuskotlin.marketplace.mappers.v2.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.otuskotlin.marketplace.api.v2.models.AdDeleteRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.AdUpdateRequest
import ru.otus.otuskotlin.marketplace.common.helpers.errorMapping
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.mappers.v2.MapContext

internal fun ICorAddExecDsl<MapContext>.takeLock(title: String) = worker {
    this.title = title
    on { state == MapContext.MapState.RUNNING }
    handle {
        when(val r = req) {
            is AdUpdateRequest -> adResult.lock = MkplAdLock(r.ad?.lock ?: "")
            is AdDeleteRequest -> adResult.lock = MkplAdLock(r.ad?.lock ?: "")
            else -> errorMapping(
                field = "id",
                violationCode = "wrongRequest",
                description = "invalid request contains no required field"
            )
        }
    }
}
