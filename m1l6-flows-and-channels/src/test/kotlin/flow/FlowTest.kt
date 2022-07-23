package flow

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.time.Instant
import kotlin.random.Random

class FlowTest {

    @Test
    fun test1(): Unit = runBlocking {
        val random = Random.Default
        val seq = sequence {
            while (true) {
                yield(random.nextDouble())
            }
        }

        val detectors = listOf(
            CoroutineDetector("coroutine", seq, 500L),
            BlockingDetector("blocking", seq, 800L),
            CallbackDetector("callback", seq, 2_000L)
        )

        detectors
            .map { it.samples() }
            .merge()
            .onEach { println(it) }
            .launchIn(this)

        delay(2000)
        coroutineContext.cancelChildren()
    }

    @Test
    fun test2(): Unit = runBlocking {
        val random = Random.Default
        val seq = sequence {
            while (true) {
                yield(random.nextDouble())
            }
        }

        val detectors = listOf(
            CoroutineDetector("coroutine", seq, 500L),
            BlockingDetector("blocking", seq, 800L),
            CallbackDetector("callback", seq, 2_000L)
        )

        val desiredPeriod = 1000L
        detectors
            .map {
                it.samples()
                    .transformLatest { sample ->
//                    println("Start transformLatest for ${sample.serialNumber}")
                        emit(sample)
                        while (true) {
                            delay(desiredPeriod)
//                        println("Add old value to flow in transformLatest for = ${sample.serialNumber}")
                            emit(sample.copy(timestamp = Instant.now()))
                        }
                    }
                    .sample(desiredPeriod)
            }
            .merge()
            .onEach { println(it) }
            .launchIn(this)

        delay(5_000)
        coroutineContext.cancelChildren()
    }

    @Test
    fun test3(): Unit = runBlocking {
        val random = Random.Default
        val seq = sequence {
            while (true) {
                yield(random.nextDouble())
            }
        }

        val detectors = listOf(
            CoroutineDetector("coroutine", seq, 500L),
            BlockingDetector("blocking", seq, 800L),
            CallbackDetector("callback", seq, 2_000L)
        )

        val desiredPeriod = 1000L
        val samples = detectors
            .map {
                it.samples()
                    .transformLatest { sample ->
//                    println("Start transformLatest for ${sample.serialNumber}")
                        emit(sample)
                        while (true) {
                            delay(desiredPeriod)
//                        println("Add old value to flow in transformLatest for = ${sample.serialNumber}")
                            emit(sample.copy(timestamp = Instant.now()))
                        }
                    }
                    .sample(desiredPeriod)
            }
            .merge()
            .shareIn(this, SharingStarted.Lazily)

        samples
            .rollingMax(compareBy { it.value })
            .sample(desiredPeriod)
            .onEach { println(it) }
            .launchIn(this)

        delay(5_000)
        coroutineContext.cancelChildren()
    }

    @Test
    fun test4(): Unit = runBlocking {
        val random = Random.Default
        val seq = sequence {
            while (true) {
                yield(random.nextDouble())
            }
        }

        val detectors = listOf(
            CoroutineDetector("coroutine", seq, 500L),
            BlockingDetector("blocking", seq, 800L),
            CallbackDetector("callback", seq, 2_000L)
        )

        val desiredPeriod = 1000L
        val flows = detectors
            .map {
                it.samples()
                    .transformLatest { sample ->
                        emit(sample)
                        while (true) {
                            delay(desiredPeriod)
                            emit(sample.copy(timestamp = Instant.now()))
                        }
                    }
                    .sample(desiredPeriod)
            }

        var index = 0
        val samples = combineTransform(flows) {
            it.forEach { s -> println("$index: value = $s") }
            index++

            emit(it.maxBy { s -> s.value })
        }
            .shareIn(this, SharingStarted.Lazily)

        samples
            .onEach { println(it) }
            .launchIn(this)

        delay(5_000)
        coroutineContext.cancelChildren()
    }
}