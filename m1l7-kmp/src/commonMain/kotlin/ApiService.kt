import kotlinx.coroutines.delay

class ApiService {
    suspend fun call(): String {
        delay(5000)
        return "Api call response"
    }
}