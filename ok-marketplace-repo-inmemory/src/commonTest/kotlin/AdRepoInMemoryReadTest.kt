import ru.otus.otuskotlin.marketplace.backend.repo.common.RepoAdReadTest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class AdRepoInMemoryReadTest: RepoAdReadTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
