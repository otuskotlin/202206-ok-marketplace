import kotlinx.coroutines.delay

actual class CommonApiService {
    actual suspend fun makeCall(): String {
        delay(1000)
        return "JVM api call"
    }
}