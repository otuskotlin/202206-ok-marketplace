import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class Exercise1KtTest {
    @Test
    fun rectangleArea() {
        /*val r = Rectangle(10, 20)
        assertEquals(r.area(), 200)
        assertEquals(r.width, 10)
        assertEquals(r.height, 20)*/
    }

    @Test
    fun rectangleToString() {
        /*val r = Rectangle(10, 20)
        assertEquals(r.toString(), "Rectangle(10x20)")
        */
    }

    @Test
    fun rectangleEquals() {
        /*val a = Rectangle(10, 20)
        val b = Rectangle(10, 20)
        val c = Rectangle(20, 10)
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        assertFalse (a === b)
        assertNotEquals(a, c)
        */
    }

    @Test
    fun figureArea() {
        /*var f : Figure = Rectangle(10, 20)
        assertEquals(f.area(), 200)

        f = Square(10)
        assertEquals(f.area(), 100)
        */
    }

    @Test
    fun diffArea() {
        /*val a = Rectangle(10, 20)
        val b = Square(10)
        assertEquals(diffArea(a, b), 100)
        */
    }

    @Test
    fun squareEquals() {
        /*val a = Square(10)
        val b = Square(10)
        val c = Square(20)
        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
        assertFalse (a === b)
        assertNotEquals(a, c)
        println(a)
        */
    }
}