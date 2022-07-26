import kotlinx.coroutines.delay

actual class CommonApiService {
    actual suspend fun makeCall(): String {
        delay(1000)
        return "JS api call"
    }
}