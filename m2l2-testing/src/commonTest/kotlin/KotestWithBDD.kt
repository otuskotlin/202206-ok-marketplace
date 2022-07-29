import io.kotest.core.spec.style.BehaviorSpec

class KotestWithBDD : BehaviorSpec({
    Given("State A") {
        println("state A ")
        When("Action A") {
            println("in action A")
            Then("State => A1") {
                println("becomes A1")
            }
        }
        When("Action B") {
            println("in action B")
            Then("State => B1") {
                println("becomes B1")
            }
        }
    }
})
