package ru.otus.otuskotlin.marketplace.lib.koform

import io.konform.validation.Invalid
import io.konform.validation.Validation
import io.konform.validation.jsonschema.pattern
import kotlinx.datetime.Instant
import org.junit.Test
import kotlin.test.assertIs

class KonformTest {
    @Test
    fun konform() {
        val obj = SomeObject()
        val objValidator = Validation<SomeObject> {
            SomeObject::userId {
                pattern("^[0-9a-zA-Z_-]{1,64}\$")
            }
//            SomeObject::dob {
//                minValue(System.Clock.now() - 120.years)
//            }
        }

        val result = objValidator.validate(obj)
        assertIs<Invalid<SomeObject>>(result)
        result as Invalid<SomeObject>
        val error = result.errors.firstOrNull()
        println("RESULT: ${error?.dataPath} ${error?.message}")
    }
}

data class SomeObject(
    val userId: String = "",
    val dob: Instant? = null,
)
