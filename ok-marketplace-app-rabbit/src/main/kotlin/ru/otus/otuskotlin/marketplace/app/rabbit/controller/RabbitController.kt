package ru.otus.otuskotlin.marketplace.app.rabbit.controller

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import ru.otus.otuskotlin.marketplace.app.rabbit.RabbitProcessorBase
import java.util.concurrent.Executors

class RabbitController(
    private val processors: Set<RabbitProcessorBase>
) {
    private val scope = CoroutineScope(
        Executors.newSingleThreadExecutor()
            .asCoroutineDispatcher() + CoroutineName("thread-rabbitmq-controller")
    )

    fun start() = scope.launch {
        processors.forEach {
            launch(
                Executors.newSingleThreadExecutor()
                    .asCoroutineDispatcher() + CoroutineName("thread-${it.processorConfig.consumerTag}")
            ) {
                try {
                    it.process()
                } catch (e: RuntimeException) {
                    // логируем, что-то делаем
                    e.printStackTrace()
                }
            }
        }
    }
}
