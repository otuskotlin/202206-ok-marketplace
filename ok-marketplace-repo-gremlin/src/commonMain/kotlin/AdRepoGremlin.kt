package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import com.benasher44.uuid.uuid4
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.repo.*

expect class AdRepoGremlin(
    hosts: String,
    port: Int = 8182,
    enableSsl: Boolean = true,
    initObjects: List<MkplAd> = emptyList(),
    randomUuid: () -> String = { uuid4().toString() }
) : IAdRepository {

    override suspend fun createAd(rq: DbAdRequest): DbAdResponse
    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse
    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse
    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse
    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse
}
