import ru.otus.otuskotlin.marketplace.backend.repo.common.RepoAdDeleteTest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class AdRepoInMemoryDeleteTest: RepoAdDeleteTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
