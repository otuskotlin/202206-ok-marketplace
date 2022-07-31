import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldContainOnlyDigits

class UUIDTestJS : StringSpec({
    "date should starts with year" {
        println(currentDate().iso) // sample for sout in js
        currentDate().iso
            .take(4)
            .shouldContainOnlyDigits()
    }

    "date should contains separator" {
        currentDate().iso shouldContain "T"
    }

    "date should contains date and time" {
        val data = currentDate().iso
            .split("T")

        //simple checks
        data.size shouldBe 2

        data.first().filter { it == '-' }.length shouldBe 2
        data.last().filter { it == ':' } shouldBe "::"
    }
})
