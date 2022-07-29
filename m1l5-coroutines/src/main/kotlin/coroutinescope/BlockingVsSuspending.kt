package coroutinescope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.suspendingCall(ctx: CoroutineContext) =
    launch(ctx) {
        println("Start_delay")
        delay(500)
        println("foo bar")
    }

fun CoroutineScope.blockingCall(ctx: CoroutineContext) =
    launch(ctx) {
        runBlocking {
            println("Taking delay")
            delay(500)
            println("foo bar")
        }
    }

fun main() {
    runBlocking {
        val ctx = newSingleThreadContext("MyOwnThread")
        repeat(10) {
            suspendingCall(ctx)
//            blockingCall(ctx)
        }
    }
}