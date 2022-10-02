import ru.otus.otuskotlin.marketplace.backend.repo.common.RepoAdUpdateTest
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository

class AdRepoInMemoryUpdateTest: RepoAdUpdateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
