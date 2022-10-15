package ru.otus.otuskotlin.marketplace.backend.repository.inmemory

import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.repo.*
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub

class AdRepoStub() : IAdRepository {
    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = MkplAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = MkplAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = MkplAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = MkplAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        return DbAdsResponse(
            data = MkplAdStub.prepareSearchList(filter = "", MkplDealSide.DEMAND),
            isSuccess = true,
        )
    }
}
