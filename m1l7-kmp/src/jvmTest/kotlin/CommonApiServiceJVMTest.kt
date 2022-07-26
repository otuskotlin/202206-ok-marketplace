import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CommonApiServiceJVMTest {

    @Test
    fun test1() = runTest {
        assertEquals("JVM api call", CommonApiService().makeCall())
    }
}