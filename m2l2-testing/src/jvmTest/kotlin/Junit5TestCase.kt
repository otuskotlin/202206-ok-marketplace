import org.junit.jupiter.api.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals


internal class Junit5TestCase {
    @Test
    fun `simple test`() {
        val num1 = 123
        val num2 = 321

        // using kotlin.test
        assertEquals(444, num1 + num2)
        assertEquals(444, num1 + num2, "Plus is not working")

        // using jupiter
        Assertions.assertEquals(444, num1 + num2, "Plus is not working")
        Assertions.assertEquals(444, num1 + num2) {
            // do not create string if not necessary
            "Plus is not working"
        }
    }
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Junit5LifeCycleTestCase {
    lateinit var operation: String

    @BeforeTest
    fun setUpFixture() {
        operation = "*"
        println(operation)
    }

    @Test
    fun `check operation`() {
        Assertions.assertEquals(operation, "*")

        val num1 = 123
        val num2 = 321

        // using jupiter
        Assertions.assertEquals(444, num1 + num2, "Plus is not working")
    }

    @AfterTest
    fun tearDownFixture() {
        operation = "NONE"
        println(operation)
    }

    @AfterEach
    fun afterEach() {
        println("Tes is done!")
    }

    @AfterEach
    fun beforeEach() {
        println("Tes is ready!")
    }
}

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class Junit5OrderTestCase {
    @Test
    @DisplayName("my custom first test")
    @Order(1)
    fun firstTest() {
        // ...
    }

    @Test
    @DisplayName("my custom second test with super-cool name")
    @Order(2)
    fun secondTest() {
        // ...
    }
}
