import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Junit5ParamTestCase {
    @ParameterizedTest
    @MethodSource("numbers")
    fun `test multiply`(a: Int, b: Int, expected: Int) {
        Assertions.assertEquals(expected, a * b)
    }

    @ParameterizedTest(name = "pair {index}: {0} and {1}")
    @MethodSource("numbers")
    fun `test multiply with custom name`(a: Int, b: Int, expected: Int) {
        Assertions.assertEquals(expected, a * b)
    }

    companion object {
        @JvmStatic
        fun numbers() = listOf(
            Arguments.of(1, 1, 1),
            Arguments.of(3, 14, 42),
            Arguments.of(2, 71, 142),
        )
    }
}
