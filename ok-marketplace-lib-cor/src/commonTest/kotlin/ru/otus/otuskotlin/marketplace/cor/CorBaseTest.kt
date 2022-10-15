package ru.otus.otuskotlin.marketplace.cor

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.marketplace.cor.handlers.CorChain
import ru.otus.otuskotlin.marketplace.cor.handlers.CorWorker
import ru.otus.otuskotlin.marketplace.cor.handlers.executeSequential
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

@OptIn(ExperimentalCoroutinesApi::class)
class CorBaseTest {
    @Test
    @JsName("worker_should_execute_handle")
    fun `worker should execute handle`() = runTest {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { history += "w1; " }
        )
        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("w1; ", ctx.history)
    }

    @Test
    @JsName("worker_should_not_execute_when_off")
    fun `worker should not execute when off`() = runTest {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockOn = { status == CorStatuses.ERROR },
            blockHandle = { history += "w1; " }
        )
        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("", ctx.history)
    }

    @Test
    @JsName("worker_should_handle_exception")
    fun `worker should handle exception`() = runTest {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { throw RuntimeException("some error") },
            blockExcept = { e -> history += e.message }
        )
        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("some error", ctx.history)
    }

    @Test
    @JsName("chain_should_execute_workers")
    fun `chain should execute workers`() = runTest {
        val createWorker = { title: String ->
            CorWorker<TestContext>(
                title = title,
                blockOn = { status == CorStatuses.NONE },
                blockHandle = { history += "$title; " }
            )
        }
        val chain = CorChain<TestContext>(
            execs = listOf(createWorker("w1"), createWorker("w2")),
            title = "chain",
            handler = ::executeSequential
        )
        val ctx = TestContext()
        chain.exec(ctx)
        assertEquals("w1; w2; ", ctx.history)
    }

    private suspend fun execute(dsl: ICorExecDsl<TestContext>): TestContext {
        val ctx = TestContext()
        dsl.build().exec(ctx)
        return ctx
    }

    @Test
    @JsName("handle_should_execute")
    fun `handle should execute`() = runTest {
        assertEquals("w1; ", execute(rootChain {
            worker {
                handle { history += "w1; " }
            }
        }).history)
    }

    @Test
    @JsName("on_should_check_condition")
    fun `on should check condition`() = runTest {
        assertEquals("w2; w3; ", execute(rootChain {
            worker {
                on { status == CorStatuses.ERROR }
                handle { history += "w1; " }
            }
            worker {
                on { status == CorStatuses.NONE }
                handle {
                    history += "w2; "
                    status = CorStatuses.FAILING
                }
            }
            worker {
                on { status == CorStatuses.FAILING }
                handle { history += "w3; " }
            }
        }).history)
    }

    @Test
    @JsName("except_should_execute_when_exception")
    fun `except should execute when exception`() = runTest {
        assertEquals("some error", execute(rootChain {
            worker {
                handle { throw RuntimeException("some error") }
                except { history += it.message }
            }
        }).history)
    }

    @Test
    @JsName("should_throw_when_exception_and_no_except")
    fun `should throw when exception and no except`() = runTest {
        assertFails {
            execute(rootChain {
                worker("throw") { throw RuntimeException("some error") }
            })
        }
    }

    @Test
    @JsName("complex_chain_example")
    fun `complex chain example`() = runTest {
        val chain = rootChain<TestContext> {
            worker {
                title = "Инициализация статуса"
                description = "При старте обработки цепочки, статус еще не установлен. Проверяем его"

                on { status == CorStatuses.NONE }
                handle { status = CorStatuses.RUNNING }
                except { status = CorStatuses.ERROR }
            }

            chain {
                on { status == CorStatuses.RUNNING }

                worker(
                    title = "Лямбда обработчик",
                    description = "Пример использования обработчика в виде лямбды"
                ) {
                    some += 4
                }
            }

            parallel {
                on {
                    some < 15
                }

                worker(title = "Increment some") {
                    some++
                }
            }

            printResult()

        }.build()

        val ctx = TestContext()
        chain.exec(ctx)
        println("Complete: $ctx")
    }
}


private fun ICorChainDsl<TestContext>.printResult() = worker(title = "Print example") {
    println("some = $some")
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
    ERROR
}
