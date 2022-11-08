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
import ru.otus.otuskotlin.marketplace.common.repo.DbAdsResponse
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoOffersTest {

    private val userId = MkplUserId("321")
    private val command = MkplCommand.OFFERS
    private val initAd = MkplAd(
        id = MkplAdId("123"),
        title = "abc",
        description = "abc",
        ownerId = userId,
        adType = MkplDealSide.DEMAND,
        visibility = MkplVisibility.VISIBLE_PUBLIC,
    )
//    private val noneTypeAd = MkplAd(
//        id = MkplAdId("213"),
//        title = "abc",
//        description = "abc",
//        adType = MkplDealSide.NONE,
//        visibility = MkplVisibility.VISIBLE_PUBLIC,
//    )
    private val offerAd = MkplAd(
        id = MkplAdId("321"),
        title = "abcd",
        description = "xyz",
        adType = MkplDealSide.SUPPLY,
        visibility = MkplVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepositoryMock(
        invokeReadAd = {
            DbAdResponse(
                isSuccess = true,
                data = initAd
            )
        },
        invokeSearchAd = {
            DbAdsResponse(
                isSuccess = true,
                data = listOf(offerAd)
            )
        }
    ) }
    private val settings by lazy {
        MkplSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { MkplAdProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersSuccessTest() = runTest {
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            adRequest = MkplAd(
                id = MkplAdId("123"),
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
        assertEquals(1, ctx.adsResponse.size)
        assertEquals(MkplDealSide.SUPPLY, ctx.adsResponse.first().adType)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersNotFoundTest() = repoNotFoundTest(command)
}
