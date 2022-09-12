package ru.otus.otuskotlin.marketplace.mappers.v2

import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdFilter
import ru.otus.otuskotlin.marketplace.common.models.MkplError

internal data class MapContext(
    var state: MapState = MapState.NONE,
    val errors: MutableList<MkplError> = mutableListOf(),

    var req: IRequest? = null,
    var adResult: MkplAd = MkplAd(),
    var adFilterResult: MkplAdFilter = MkplAdFilter(),
) {
    enum class MapState {
        NONE,
        RUNNING,
        @Suppress("unused")
        FINISHING,
        FAILING,
    }

}
