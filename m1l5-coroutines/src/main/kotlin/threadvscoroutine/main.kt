import kotlinx.coroutines.runBlocking
import threadvscoroutine.runCoroutine

fun main() {
//    runThread()
    runBlocking {
        runCoroutine()
        runCoroutine()
        runCoroutine()
    }
}