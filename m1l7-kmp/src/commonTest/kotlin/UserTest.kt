import kotlin.test.Test
import kotlin.test.assertEquals

class UserTest {

    @Test
    fun test1() {
        val user = User("1", "Ivan", 24)
        assertEquals("1", user.id)
        assertEquals("Ivan", user.name)
        assertEquals(24, user.age)
    }
}