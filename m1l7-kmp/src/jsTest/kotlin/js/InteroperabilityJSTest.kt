package js

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class InteroperabilityJSTest {

    @Test
    fun mathRoundTest() {
        assertEquals(3, useMathRound(PI))
    }

    @Test
    fun sortTest() {
        assertEquals(true, sorted(arrayOf(1, 2, 3)))
        assertEquals(false, sorted(arrayOf(4, 1, 2, 3)))
    }

//    @Test
//    fun bigDecimalTest() {
//        val bd = JsBigDecimal("${PI * 10000}")
//        println("BigDecimal: ${bd.getValue()}")
//        println("Pretty: ${bd.getPrettyValue(3, ",")}")
//        println("Pretty static: ${JsBigDecimal.getPrettyValue(bd.getValue(), 3, ",")}")
//        assertEquals("31416", bd.round().getValue())
//    }

    @Test
    fun dukatLibraryTest() {
        val bd = bigDecimal("${PI * 10000}")
        println("BigDecimal: ${bd.getValue()}")
        println("Pretty: ${bd.getPrettyValue(3, ",")}")
        println("Pretty static: ${bigDecimal.getPrettyValue(bd.getValue(), 3, ",")}")
        assertEquals("31416", bd.round().getValue())
    }
}