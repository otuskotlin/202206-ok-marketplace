package ru.otus.otuskotlin.marketplace.biz.repo

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.MkplState
import ru.otus.otuskotlin.marketplace.common.repo.DbAdRequest
import ru.otus.otuskotlin.marketplace.cor.ICorChainDsl
import ru.otus.otuskotlin.marketplace.cor.worker

fun ICorChainDsl<MkplContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == MkplState.RUNNING }
    handle {
        val request = DbAdRequest(adRepoPrepare)
        val result = adRepo.updateAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = MkplState.FAILING
            errors.addAll(result.errors)
            adRepoDone
        }
    }
}
