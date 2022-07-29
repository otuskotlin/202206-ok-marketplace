import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Junit5DDTTestCase {

    @TestFactory
    fun `test multi`() = listOf(
        DynamicTest.dynamicTest("when I multiply 10*2 then I get 20") {
            Assertions.assertEquals(20, 10 * 2)
        },
        DynamicTest.dynamicTest("when I multiply 10*0 then I get 0") {
            Assertions.assertEquals(0, 10 * 0)
        },
    )

    private val data = listOf(
        Triple(1, 13, 13),
        Triple(2, 21, 42),
        Triple(3, 34, 102),
        Triple(4, 55, 220),
        Triple(5, 89, 445),
    )

    @TestFactory
    fun testSquares() = data.map { (a, b, expected) ->
        DynamicTest.dynamicTest("when I multiply $a*$b then I get $expected") {
            Assertions.assertEquals(expected, a * b)
        }
    }
}
