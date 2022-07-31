import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MessageTestCase {
    @Test
    fun `test message is string`() {
        val msg = getMessage()

        Assertions.assertInstanceOf(String::class.java, msg)
    }

    @Test
    fun `test message with name`() {
        val msg = getMessage("Bob")

        Assertions.assertEquals("Hello Bob!", msg)
    }

    @Test
    fun `test message with another name`() {
        val msg = getMessage("Ann")

        Assertions.assertEquals("Hello Ann!", msg)
    }

    @Test
    fun `test message with empty name`() {
        val msg = getMessage()

        Assertions.assertEquals("Hello, my friend!", msg)
    }

    @Test
    fun `test message with CAPS name`() {
        val msg = getMessage("JANE")

        Assertions.assertEquals("HELLO JANE!", msg)
    }

    @Test
    fun `test message with multiple names`() {
        val msg = getMessage("Bob", "Anna")

        Assertions.assertEquals("Hello Bob, Anna!", msg)
    }

    @Test
    fun `test message with multiple names with CAPS`() {
        val msg = getMessage("Bob", "Anna", "JANE")

        Assertions.assertEquals("HELLO BOB, ANNA, JANE!", msg)
    }
}
