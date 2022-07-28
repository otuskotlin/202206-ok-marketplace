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

external enum class RoundingModes {
    CEILING /* = 0 */,
    DOWN /* = 1 */,
    FLOOR /* = 2 */,
    HALF_DOWN /* = 3 */,
    HALF_EVEN /* = 4 */,
    HALF_UP /* = 5 */,
    UNNECESSARY /* = 6 */,
    UP /* = 7 */
}