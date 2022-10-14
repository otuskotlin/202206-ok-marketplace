package ru.otus.otuskotlin.marketplace.common.repo

interface IAdRepository {
    suspend fun createAd(rq: DbAdRequest): DbAdResponse
    suspend fun readAd(rq: DbAdIdRequest): DbAdResponse
    suspend fun updateAd(rq: DbAdRequest): DbAdResponse
    suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse
    suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse
    companion object {
        val NONE = object : IAdRepository {
            override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
                TODO("Not yet implemented")
            }

            override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
                TODO("Not yet implemented")
            }

            override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
                TODO("Not yet implemented")
            }

            override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
                TODO("Not yet implemented")
            }

            override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
                TODO("Not yet implemented")
            }
        }
    }
}
