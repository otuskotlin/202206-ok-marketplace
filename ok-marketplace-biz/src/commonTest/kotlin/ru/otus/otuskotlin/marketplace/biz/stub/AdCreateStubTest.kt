package ru.otus.otuskotlin.marketplace.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.MkplStubs
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AdCreateStubTest {

    private val processor = MkplAdProcessor()

    @Test
    fun create() = runTest {
        val id = MkplAdId("777")
        val title = "title 777"
        val description = "desc 777"
        val dealSide = MkplDealSide.DEMAND
        val visibility = MkplVisibility.VISIBLE_PUBLIC

        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            adRequest = MkplAd(
                id = id,
                title = title,
                description = description,
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(id, ctx.adResponse.id)
        assertEquals(title, ctx.adResponse.title)
        assertEquals(description, ctx.adResponse.description)
        assertEquals(dealSide, ctx.adResponse.adType)
        assertEquals(visibility, ctx.adResponse.visibility)
    }

    @Test
    fun wrongTitle() = runTest {
        val id = MkplAdId("777")
        val title = ""
        val description = "desc 777"
        val dealSide = MkplDealSide.DEMAND
        val visibility = MkplVisibility.VISIBLE_PUBLIC

        val ctx = MkplContext(
            command = MkplCommand.CREATE,
            state = MkplState.NONE,
            workMode = MkplWorkMode.STUB,
            stubCase = MkplStubs.SUCCESS,
            adRequest = MkplAd(
                id = id,
                title = title,
                description = description,
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplAd(), ctx.adResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals(dealSide, ctx.adResponse.adType)
        assertEquals(visibility, ctx.adResponse.visibility)
    }
}
