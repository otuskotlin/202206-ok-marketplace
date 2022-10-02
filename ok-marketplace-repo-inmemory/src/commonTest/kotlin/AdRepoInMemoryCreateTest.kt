import ru.otus.otuskotlin.marketplace.backend.repo.common.RepoAdCreateTest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class AdRepoInMemoryCreateTest: RepoAdCreateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
