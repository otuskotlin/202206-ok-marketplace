import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Junit5ComplexTestCase {

    @Test
    fun `simple test`() {
        val args = listOf(
            "my line is whaat?" to "my line is whaat?",
            " line is whaat?" to "line is whaat?",
            "line is whaat?" to "line is whaat?",
            " is whaat?" to "is whaat?",
            "is whaat?" to "is whaat?",
            " whaat?" to "whaat?",
            "whaat?" to "whaat?",
            "?" to "?",
        )

        assertAll("Trimmed lines", *args.map { (expected, actual) ->
            { assertEquals(expected.trimStart(), actual) }
        }.toTypedArray())
    }

    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun `test dividing by zero`() {
        val exception = Assertions.assertThrows(ArithmeticException::class.java) {
            5 / 0
        }

        Assertions.assertNotNull(exception.message)
        Assertions.assertTrue(exception.message!!.contains("by zero"))
    }

    @Test
    fun `test supplier`() {
        val str = "my line"
        Assertions.assertFalse(str::isEmpty)
        Assertions.assertTrue(str::isNotBlank)
    }
}
