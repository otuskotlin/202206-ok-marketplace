package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlowVsSequenceTest {

    private fun simpleSequence(): Sequence<Int> = sequence {
        for (i in 1..5) {
//            delay(1000) // can't use it here
            Thread.sleep(1000)
            yield(i)
        }
    }

    private fun simpleFlow(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(1000)
            emit(i)
        }
    }

    @Test
    fun sequenceTest(): Unit = runBlocking {
        launch {
            for (k in 1..5) {
                println("I'm not blocked $k")
                delay(1000)
            }
        }
        simpleSequence().forEach { println(it) }
    }

    @Test
    fun flowTest(): Unit = runBlocking {
        launch {
            for (k in 1..5) {
                println("I'm not blocked $k")
                delay(1000)
            }
        }
        simpleFlow()
            .collect { println(it) }

        println("Flow end")
    }
}