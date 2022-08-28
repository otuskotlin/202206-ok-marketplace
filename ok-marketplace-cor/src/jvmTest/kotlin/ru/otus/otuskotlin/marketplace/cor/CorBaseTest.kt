package ru.otus.otuskotlin.marketplace.cor

import kotlinx.coroutines.runBlocking
import ru.otus.otuskotlin.marketplace.cor.handlers.CorChain
import ru.otus.otuskotlin.marketplace.cor.handlers.CorWorker
import ru.otus.otuskotlin.marketplace.cor.handlers.executeSequential
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CorBaseTest {
    @Test
    fun `worker should execute handle`() = runBlocking {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { history += "w1; " }
        )
        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("w1; ", ctx.history)
    }

}

data class TestContext(
    var status: CorStatuses = CorStatuses.NONE,
    var some: Int = 0,
    var history: String = "",
)

enum class CorStatuses {
    NONE,
    RUNNING,
    FAILING,
    DONE,
    ERROR
}
