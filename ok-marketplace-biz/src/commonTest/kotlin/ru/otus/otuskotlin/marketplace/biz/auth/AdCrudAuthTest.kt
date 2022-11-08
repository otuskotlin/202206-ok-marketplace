package ru.otus.otuskotlin.marketplace.biz.auth

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.common.MkplContext
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.permissions.MkplAdPermissionClient
import ru.otus.otuskotlin.marketplace.common.permissions.MkplPrincipalModel
import ru.otus.otuskotlin.marketplace.common.permissions.MkplUserGroups
import ru.otus.otuskotlin.marketplace.repo.inmemory.AdRepoInMemory
import ru.otus.otuskotlin.marketplace.stubs.MkplAdStub
import kotlin.test.*

/**
 * @crud - экземпляр класса-фасада бизнес-логики
 * @context - контекст, смапленный из транспортной модели запроса
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AdCrudAuthTest {
    @Test
    fun createSuccessTest() = runTest {
        val userId = MkplUserId("123")
        val repo = AdRepoInMemory()
        val processor = MkplAdProcessor(
            settings = MkplSettings(
                repoTest = repo
            )
        )
        val context = MkplContext(
            workMode = MkplWorkMode.TEST,
            adRequest = MkplAdStub.prepareResult {
                permissionsClient.clear()
                id = MkplAdId.NONE
            },
            command = MkplCommand.CREATE,
            principal = MkplPrincipalModel(
                id = userId,
                groups = setOf(
                    MkplUserGroups.USER,
                    MkplUserGroups.TEST,
                )
            )
        )

        processor.exec(context)
        assertEquals(MkplState.FINISHING, context.state)
        with(context.adResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, MkplAdPermissionClient.READ)
            assertContains(permissionsClient, MkplAdPermissionClient.UPDATE)
            assertContains(permissionsClient, MkplAdPermissionClient.DELETE)
//            assertFalse { permissionsClient.contains(PermissionModel.CONTACT) }
        }
    }

    @Test
    fun readSuccessTest() = runTest {
        val adObj = MkplAdStub.get()
        val userId = adObj.ownerId
        val adId = adObj.id
        val repo = AdRepoInMemory(initObjects = listOf(adObj))
        val processor = MkplAdProcessor(
            settings = MkplSettings(
                repoTest = repo
            )
        )
        val context = MkplContext(
            command = MkplCommand.READ,
            workMode = MkplWorkMode.TEST,
            adRequest = MkplAd(id = adId),
            principal = MkplPrincipalModel(
                id = userId,
                groups = setOf(
                    MkplUserGroups.USER,
                    MkplUserGroups.TEST,
                )
            )
        )
        processor.exec(context)
        assertEquals(MkplState.FINISHING, context.state)
        with(context.adResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, MkplAdPermissionClient.READ)
            assertContains(permissionsClient, MkplAdPermissionClient.UPDATE)
            assertContains(permissionsClient, MkplAdPermissionClient.DELETE)
//            assertFalse { context.responseAd.permissions.contains(PermissionModel.CONTACT) }
        }
    }

}
