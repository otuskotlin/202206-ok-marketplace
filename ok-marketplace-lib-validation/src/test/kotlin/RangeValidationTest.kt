package ru.otus.otuskotlin.marketplace.validation

import org.junit.Test
import ru.otus.otuskotlin.marketplace.validation.validators.ValidatorInRange
import ru.otus.otuskotlin.marketplace.validation.validators.ValidatorStringNonEmpty
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class RangeValidationTest {
    @Test
    fun inRange() {
        val validator = ValidatorInRange<Long>("age", 15L, 130L)
        val result = validator validate 8L
        assertEquals(false, result.isSuccess)
        assertContains(result.errors.firstOrNull()?.message ?: "", "age")
    }

    @Test
    fun user() {
        val validator = ComplexValidation("user")
        val user = User("", 0)
        val result = validator validate user
        assertFalse(result.isSuccess)
        assertEquals(2, result.errors.size)
    }

    class ComplexValidation(
        val field: String = "",
    ) : IValidator<User> {

        private val nameValidator: ValidatorStringNonEmpty = ValidatorStringNonEmpty("name")
        private val ageValidator: ValidatorInRange<Int> = ValidatorInRange("age", 15, 130)
        override fun validate(sample: User): ValidationResult {
            val resultName = nameValidator validate sample.name
            val resultAge = ageValidator validate sample.age
            return if (resultAge.isSuccess && resultName.isSuccess) return ValidationResult.SUCCESS
            else ValidationResult(
                listOf(resultName.errors, resultAge.errors).flatten()
            )
        }

    }

    data class User(
        val name: String = "",
        val age: Int = -1,
    )
}
