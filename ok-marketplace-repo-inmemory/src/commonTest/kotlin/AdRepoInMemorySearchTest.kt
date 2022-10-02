import ru.otus.otuskotlin.marketplace.backend.repo.common.RepoAdSearchTest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class AdRepoInMemorySearchTest: RepoAdSearchTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
