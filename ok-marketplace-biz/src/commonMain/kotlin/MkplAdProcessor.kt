package ru.otus.otuskotlin.marketplace.biz

import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.cor.rootChain

class MkplAdProcessor() {
    suspend fun exec(ctx: MkplContext) = BuzinessChain.exec(ctx)

    companion object {
        private val BuzinessChain = rootChain<MkplContext> {

        }.build()
    }
}
