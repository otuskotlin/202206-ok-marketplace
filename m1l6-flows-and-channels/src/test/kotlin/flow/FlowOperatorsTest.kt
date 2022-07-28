package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import org.junit.jupiter.api.Test

class FlowOperatorsTest {

    @Test
    fun test1(): Unit = runBlocking {
        flowOf(1, 2, 3, 4)
            .onEach { println(it) }
            .map { it + 1 }
            .filter { it % 2 == 0 }
            .collect { println("Result number $it") }
    }


    @OptIn(ObsoleteCoroutinesApi::class)
    private val ApiDispatcher = newSingleThreadContext("Api-Thread")

    @OptIn(ObsoleteCoroutinesApi::class)
    private val DbDispatcher = newSingleThreadContext("Db-Thread")

    fun <T> Flow<T>.printThreadName(msg: String) =
        this.onEach { println("Msg = $msg, thread name = ${Thread.currentThread().name}") }

    @Test
    fun test2(): Unit = runBlocking {
        flowOf(10, 20, 30)
            .filter { it % 2 == 0 }
            .map {
                delay(2000)
                it
            }
            .printThreadName("api call")
            .flowOn(ApiDispatcher)
            .map { it + 1 }
            .printThreadName("db call")
            .flowOn(DbDispatcher)
            .printThreadName("last operation")
            .onEach { println("On each $it") }
            .collect()
    }

    @Test
    fun test3(): Unit = runBlocking {
        flow {
            while (true) {
                emit(1)
                delay(1000)
                emit(2)
                delay(1000)
                emit(3)
                delay(1000)
                throw RuntimeException("Custom error!")
            }
        }
            .onStart { println("On start") }
            .onCompletion { println(" On completion") }
            .catch { println("Catch: ${it.message}") }
            .onEach { println("On each: $it") }
            .collect { }
    }

    @Test
    fun test4(): Unit = runBlocking {
        var sleepIndex = 1
        flow {
            while (sleepIndex < 3) {
                delay(500)
                emit(sleepIndex)
            }
        }
            .onEach { println("Send to flow: $it") }
            .buffer(3, BufferOverflow.DROP_LATEST)
            .onEach { println("Processing : $it") }
            .collect {
                println("Sleep")
                sleepIndex++
                delay(2_000)
            }
    }


    fun <T> Flow<T>.zipWithNext(): Flow<Pair<T, T>> = flow {
        var prev: T? = null
        collect {
            if (prev != null) emit(prev!! to it)
            prev = it
        }
    }

    @Test
    fun test5(): Unit = runBlocking {
        flowOf(1, 2, 3, 4)
            .zipWithNext()
            .collect { println(it) }
    }

    @Test
    fun test6(): Unit = runBlocking {
        val coldFlow = flowOf(100, 101, 102, 103, 104, 105).onEach { println("Cold: $it") }

        launch { coldFlow.collect() }
        launch { coldFlow.collect() }

        val hotFlow = flowOf(200, 201, 202, 203, 204, 205)
            .onEach { println("Hot: $it") }
            .shareIn(this, SharingStarted.Lazily)

        launch { hotFlow.collect() }
        launch { hotFlow.collect() }

        delay(500)
        coroutineContext.cancelChildren()
    }

    @Test
    fun test7(): Unit = runBlocking {
        val list = flow {
            emit(1)
            delay(100)
            emit(2)
            delay(100)
        }
            .onEach { println("$it") }
            .toList()

        println("List: $list")
    }

    @Test
    fun test8(): Unit = runBlocking {
        val list = flow {
            var index = 0
            // If there is an infinite loop here, while (true)
            // then we will never output to the console
            //  println("List: $list")
            while (index < 10) {
                emit(index++)
                delay(100)
            }
        }
            .onEach { println("$it") }
            .toList()

        println("List: $list")
    }
}