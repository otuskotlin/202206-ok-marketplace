import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CommonApiServiceJSTest {

    @Test
    fun test1() = runTest {
        assertEquals("JS api call", CommonApiService().makeCall())
    }
}