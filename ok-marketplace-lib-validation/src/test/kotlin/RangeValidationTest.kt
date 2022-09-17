import org.junit.Test
import ru.otus.otuskotlin.marketplace.validation.validators.ValidatorInRange
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RangeValidationTest {
    @Test
    fun inRange() {
        val validator = ValidatorInRange<Long>("age", 15L, 130L)
        val result = validator.validate(8)
        assertEquals(false, result.isSuccess)
        assertContains(result.errors.firstOrNull()?.message ?: "", "age")
    }
}
