package jvm

class InteroperabilityJava {

    @JvmName("customName")
    fun someFunction() = "JVM: someFunction"


    @JvmOverloads
    fun defaults(param1: String = "param1-default-val", param2: Int = 1, param3: Boolean = false) =
        "param1 = $param1, param2 = $param2, param3 = $param3"


    companion object {
        @JvmStatic
        fun functionOne() {
            println("InteroperabilityJava, method = functionOne")
        }
    }
}

//class MyClass(
//    @JvmField
//    val a: String = ""
//)