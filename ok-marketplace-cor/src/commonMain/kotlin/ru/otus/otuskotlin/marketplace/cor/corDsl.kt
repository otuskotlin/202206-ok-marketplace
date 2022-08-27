package ru.otus.otuskotlin.marketplace.cor

import ru.otus.otuskotlin.marketplace.cor.handlers.CorChainDsl
import ru.otus.otuskotlin.marketplace.cor.handlers.CorWorkerDsl
import ru.otus.otuskotlin.marketplace.cor.handlers.executeParallel

/**
 * Базовый билдер (dsl)
 */
@CorDslMarker
interface ICorExecDsl<T> {
    var title: String
    var description: String
    fun on(function: suspend T.() -> Boolean)
    fun except(function: suspend T.(e: Throwable) -> Unit)

    fun build(): ICorExec<T>
}

/**
 * Билдер (dsl) для цепочек (chain)
 */
@CorDslMarker
interface ICorChainDsl<T> : ICorExecDsl<T> {
    fun add(worker: ICorExecDsl<T>)
}

/**
 * Билдер (dsl) для рабочих (worker)
 */
@CorDslMarker
interface ICorWorkerDsl<T> : ICorExecDsl<T> {
    fun handle(function: suspend T.() -> Unit)
}

/**
 * Точка входа в dsl построения цепочек.
 * Элементы исполняются последовательно.
 *
 * Пример:
 * ```
 *  chain<SomeContext> {
 *      worker {
 *      }
 *      chain {
 *          worker(...) {
 *          }
 *          worker(...) {
 *          }
 *      }
 *      parallel {
 *         ...
 *      }
 *  }
 * ```
 */
fun <T> rootChain(function: ICorChainDsl<T>.() -> Unit): ICorChainDsl<T> = CorChainDsl<T>().apply(function)


/**
 * Создает цепочку, элементы которой исполняются последовательно.
 */
fun <T> ICorChainDsl<T>.chain(function: ICorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>().apply(function))
}

/**
 * Создает цепочку, элементы которой исполняются параллельно. Будьте аккуратны с доступом к контексту -
 * при необходимости используйте синхронизацию
 */
fun <T> ICorChainDsl<T>.parallel(function: ICorChainDsl<T>.() -> Unit) {
    add(CorChainDsl<T>(::executeParallel).apply(function))
}

/**
 * Создает рабочего
 */
fun <T> ICorChainDsl<T>.worker(function: ICorWorkerDsl<T>.() -> Unit) {
    add(CorWorkerDsl<T>().apply(function))
}

/**
 * Создает рабочего с on и except по умолчанию
 */
fun <T> ICorChainDsl<T>.worker(
    title: String,
    description: String = "",
    blockHandle: T.() -> Unit
) {
    add(CorWorkerDsl<T>().also {
        it.title = title
        it.description = description
        it.handle(blockHandle)
    })
}