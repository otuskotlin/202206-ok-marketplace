import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiServiceTest {

    @Test
    fun test1() =  runTest {
        assertEquals("Api call response", ApiService().call())
    }
}