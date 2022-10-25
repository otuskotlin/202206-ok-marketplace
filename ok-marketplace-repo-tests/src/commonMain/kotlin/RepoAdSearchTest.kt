package ru.otus.otuskotlin.marketplace.backend.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplDealSide
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId
import ru.otus.otuskotlin.marketplace.common.repo.DbAdFilterRequest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdSearchTest {
    abstract val repo: IAdRepository
    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchAd(DbAdFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[1], initObjects[3])
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchDealSide() = runRepoTest {
        val result = repo.searchAd(DbAdFilterRequest(dealSide = MkplDealSide.SUPPLY))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[2], initObjects[4])
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitAds("search") {

        val searchOwnerId = MkplUserId("owner-124")
        override val initObjects: List<MkplAd> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad3", adType = MkplDealSide.SUPPLY),
            createInitTestModel("ad4", ownerId = searchOwnerId),
            createInitTestModel("ad5", adType = MkplDealSide.SUPPLY),
        )
    }
}
