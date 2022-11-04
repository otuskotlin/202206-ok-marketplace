package ru.otus.otuskotlin.marketplace.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.backend.repo.tests.AdRepositoryMock
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalModel
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserGroups
import ru.otus.otuskotlin.marketplace.common.repo.DbAdResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = MkplUserId("321")
    private val command = MkplCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = AdRepositoryMock(
        invokeCreateAd = {
            DbAdResponse(
                isSuccess = true,
                data = MkplAd(
                    id = MkplAdId(uuid),
                    title = it.ad.title,
                    description = it.ad.description,
                    ownerId = userId,
                    adType = it.ad.adType,
                    visibility = it.ad.visibility,
                )
            )
        }
    )
    private val settings = MkplSettings(
        repoTest = repo
    )
    private val processor = MkplAdProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            adRequest = MkplAd(
                title = "abc",
                description = "abc",
                adType = MkplDealSide.DEMAND,
                visibility = MkplVisibility.VISIBLE_PUBLIC,
            ),
            principal = MkplPrincipalModel(
                id = userId,
                groups = setOf(
                    MkplUserGroups.USER,
                    MkplUserGroups.TEST,
                )
            ),
        )
        processor.exec(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertNotEquals(MkplAdId.NONE, ctx.adResponse.id)
        assertEquals("abc", ctx.adResponse.title)
        assertEquals("abc", ctx.adResponse.description)
        assertEquals(MkplDealSide.DEMAND, ctx.adResponse.adType)
        assertEquals(MkplVisibility.VISIBLE_PUBLIC, ctx.adResponse.visibility)
    }
}
