import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class CommonApiServiceTest {
    @Test
    fun test1() = runTest {
        println(CommonApiService().makeCall())
    }
}