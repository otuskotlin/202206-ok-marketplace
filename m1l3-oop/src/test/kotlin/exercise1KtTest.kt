import kotlin.test.Test


class exercise1KtTest {
    // задание 1 - сделайте класс Rectangle, у которого будет width и height
    // а также метод вычисления площади - area()
    // тест ниже должен пройти - раскомментируйте код в нем
    @Test
    fun rectangleArea() {
        /*val r = Rectangle(10, 20)
        assertEquals(r.area(), 200)
        assertEquals(r.width, 10)
        assertEquals(r.height, 20)*/
    }

    // задание 2 - сделайте метод Rectangle.toString()
    // тест ниже должен пройти - раскомментируйте код в нем
    @Test
    fun rectangleToString() {
        /*val r = Rectangle(10, 20)
        assertEquals(r.toString(), "Rectangle(10x20)")
        */
    }

    // задание 3 - сделайте методы Rectangle.equals() и Rectangle.hashCode()
    // тест ниже должен пройти - раскомментируйте код в нем
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

    // задание 4 - сделайте класс Square
    // тест ниже должен пройти - раскомментируйте код в нем
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

    // задание 4 - сделайте интерфейс Figure c методом area(), отнаследуйте от него Rectangle и Square
    // тест ниже должен пройти - раскомментируйте код в нем
    @Test
    fun figureArea() {
        /*var f : Figure = Rectangle(10, 20)
        assertEquals(f.area(), 200)

        f = Square(10)
        assertEquals(f.area(), 100)
        */
    }

    // задание 5 - сделайте метод diffArea(a, b)
    // тест ниже должен пройти - раскомментируйте код в нем
    @Test
    fun diffArea() {
        /*val a = Rectangle(10, 20)
        val b = Square(10)
        assertEquals(diffArea(a, b), 100)
        */
    }

}