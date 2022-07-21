package flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import java.time.Instant
import java.util.*
import kotlin.concurrent.schedule

data class Sample(
    val serialNumber: String,
    val value: Double,
    val timestamp: Instant = Instant.now()
)

interface Detector {
    fun samples(): Flow<Sample>
}

class CoroutineDetector(
    private val serialNumber: String,
    private val sampleDistribution: Sequence<Double>,
    private val samplePeriod: Long
) : Detector {
    override fun samples(): Flow<Sample> =
        flow {
            val values = sampleDistribution.iterator()
            while (true) {
                emit(Sample(serialNumber, values.next()))
                delay(samplePeriod)
            }
        }
}

class BlockingDetector(
    private val serialNumber: String,
    private val sampleDistribution: Sequence<Double>,
    private val samplePeriod: Long
) : Detector {
    override fun samples(): Flow<Sample> =
        flow {
            val values = sampleDistribution.iterator()
            while (true) {
                emit(Sample(serialNumber, values.next()))
                Thread.sleep(samplePeriod)
            }
        }.flowOn(Dispatchers.IO)
}

class CallbackDetector(
    private val serialNumber: String,
    private val sampleDistribution: Sequence<Double>,
    private val samplePeriod: Long
) : Detector {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun samples(): Flow<Sample> =
        callbackFlow {
            val values = sampleDistribution.iterator()

            val timer = Timer()
            timer.schedule(0L, samplePeriod) {
                trySendBlocking(Sample(serialNumber, values.next()))
            }
            timer.schedule(10_000L) { close() }

            awaitClose { timer.cancel() }
        }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> Flow<T>.rollingMax(comparator: Comparator<T>): Flow<T> =
    runningReduce { max, current -> maxOf(max, current, comparator) }