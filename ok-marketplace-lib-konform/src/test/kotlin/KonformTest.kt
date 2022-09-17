package ru.otus.otuskotlin.marketplace.lib.koform

import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.konform.validation.ValidationBuilder
import io.konform.validation.jsonschema.pattern
import kotlinx.datetime.*
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.days

class KonformTest {
    @Test
    fun konform() {
        val objValidator = Validation<SomeObject> {
            SomeObject::userId {
                pattern("^[0-9a-zA-Z_-]{1,64}\$")
            }
            SomeObject::dob {
                minAge(15)
            }
        }

        val resultUserId = objValidator.validate(SomeObject())
        assertIs<Invalid<SomeObject>>(resultUserId)
        val errorUserId = resultUserId.errors.firstOrNull { it.dataPath == ".userId" }
        assertContains(errorUserId?.message ?: "", "pattern")
        println("RESULT: ${errorUserId?.dataPath} ${errorUserId?.message}")

        val resultAge = objValidator.validate(SomeObject(userId = "987987987"))
        assertIs<Invalid<SomeObject>>(resultAge)
        val errorAge = resultAge.errors.firstOrNull { it.dataPath == ".dob" }
        assertContains(errorAge?.message ?: "", "Age")
        println("RESULT: ${errorAge?.dataPath} ${errorAge?.message}")
    }
}

private fun ValidationBuilder<LocalDate?>.minAge(age: Int) = addConstraint(
    errorMessage = "Age cannot be in range of 15..130 years",
    "15..130"
) {
    if (it == null) {
        println("EMPTY!")
        false
    } else {
        val date = Clock.System
            .now()
            .minus((age * 365).days)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        println("DATE: $date ~ $it ${it.compareTo(date)}")
        (it.compareTo(date) in 0..1)
    }
}

data class SomeObject(
    val userId: String = "",
    val dob: LocalDate? = null,
)
