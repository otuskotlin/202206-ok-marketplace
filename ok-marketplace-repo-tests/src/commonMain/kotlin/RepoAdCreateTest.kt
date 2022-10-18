package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdId
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId
import ru.otus.otuskotlin.marketplace.common.models.MkplVisibility
import ru.otus.otuskotlin.marketplace.common.repo.DbAdRequest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    protected open val lockNew: MkplAdLock = MkplAdLock("20000000-0000-0000-0000-000000000002")

    protected val createObj = MkplAd(
        title = "create object",
        description = "create object description",
        ownerId = MkplUserId("owner-123"),
        visibility = MkplVisibility.VISIBLE_TO_GROUP,
        adType = MkplDealSide.SUPPLY,
    )

    @Test
    fun createSuccess() = runTest {
        val result = repo.createAd(DbAdRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: MkplAdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.data?.title)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.adType, result.data?.adType)
        assertNotEquals(MkplAdId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
        assertNotNull(result.data?.lock)
    }

    companion object : BaseInitAds("create") {
        override val initObjects: List<MkplAd> = emptyList()
    }
}
