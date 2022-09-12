package ru.otus.otuskotlin.marketplace.mappers.v2.workers

import com.crowdproj.kotlin.cor.ICorAddExecDsl
import com.crowdproj.kotlin.cor.handlers.worker
import ru.otus.otuskotlin.marketplace.api.v2.models.AdSearchRequest
import ru.otus.otuskotlin.marketplace.common.helpers.errorMapping
import ru.otus.otuskotlin.marketplace.mappers.v2.MapContext

internal fun ICorAddExecDsl<MapContext>.takeSearchString(title: String) = worker {
    this.title = title
    on { state == MapContext.MapState.RUNNING }
    handle {
        when(val r = req) {
            is AdSearchRequest -> adResult.title = r.adFilter?.searchString ?: ""
            else -> errorMapping(
                field = "searchString",
                violationCode = "wrongRequest",
                description = "invalid request contains no required field"
            )
        }
    }
}
