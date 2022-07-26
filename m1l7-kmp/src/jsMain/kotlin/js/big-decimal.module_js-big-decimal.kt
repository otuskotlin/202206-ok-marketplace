@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package js

import kotlin.js.*
import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.dom.parsing.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.workers.*
import org.w3c.xhr.*

@JsModule("js-big-decimal")
@JsNonModule
open external class bigDecimal {
    open var value: Any
    constructor(number: Number = definedExternally)
    constructor()
    constructor(number: String = definedExternally)
    open fun getValue(): String
    open fun getPrettyValue(digits: Any, separator: Any): String
    open fun round(precision: Number = definedExternally, mode: RoundingModes = definedExternally): bigDecimal
    open fun floor(): bigDecimal
    open fun ceil(): bigDecimal
    open fun add(number: bigDecimal): bigDecimal
    open fun subtract(number: bigDecimal): bigDecimal
    open fun multiply(number: bigDecimal): bigDecimal
    open fun divide(number: bigDecimal, precision: Any): bigDecimal
    open fun modulus(number: bigDecimal): bigDecimal
    open fun compareTo(number: bigDecimal): dynamic /* 1 | 0 | "-1" */
    open fun negate(): bigDecimal

    companion object {
        var RoundingModes: Any
        var validate: Any
        fun getPrettyValue(number: Any, digits: Any, separator: Any): String
        fun round(number: Any, precision: Number = definedExternally, mode: RoundingModes = definedExternally): String
        fun floor(number: Any): Any
        fun ceil(number: Any): Any
        fun add(number1: Any, number2: Any): String
        fun subtract(number1: Any, number2: Any): String
        fun multiply(number1: Any, number2: Any): String
        fun divide(number1: Any, number2: Any, precision: Any): String
        fun modulus(number1: Any, number2: Any): String
        fun compareTo(number1: Any, number2: Any): dynamic /* 1 | 0 | "-1" */
        fun negate(number: Any): String
    }
}