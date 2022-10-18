package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import ru.otus.otuskotlin.marketplace.backend.repo.tests.RepoAdDeleteTest
import ru.otus.otuskotlin.marketplace.common.models.MkplAd

class AdRepoGremlinDeleteTest : RepoAdDeleteTest() {
    override val repo: AdRepoGremlin by lazy {
        AdRepoGremlin(
            hosts = ArcadeDbContainer.container.host,
            port = ArcadeDbContainer.container.getMappedPort(8182),
            enableSsl = false,
            initObjects = initObjects,
            initRepo = { g -> g.V().drop().iterate() },
        )
    }
    override val deleteSucc: MkplAd by lazy { repo.initializedObjects[0] }
    override val deleteConc: MkplAd by lazy { repo.initializedObjects[1] }
}
