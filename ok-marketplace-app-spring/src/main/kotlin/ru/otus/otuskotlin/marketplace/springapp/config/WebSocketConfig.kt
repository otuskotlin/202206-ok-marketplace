package ru.otus.otuskotlin.marketplace.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor
import ru.otus.otuskotlin.marketplace.springapp.api.v1.controller.WsAdHandlerV1
import ru.otus.otuskotlin.marketplace.springapp.api.v2.controller.WsAdHandlerV2
import ru.otus.otuskotlin.marketplace.springapp.common.SpringWsSession

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val processor: MkplAdProcessor,
) : WebSocketConfigurer {

    private val sessionsV1 = mutableMapOf<String, SpringWsSession>()
    private val sessionsV2 = mutableMapOf<String, SpringWsSession>()

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(WsAdHandlerV1(processor, sessionsV1), "/ws/v1").setAllowedOrigins("*")
        registry.addHandler(WsAdHandlerV2(processor, sessionsV2), "/ws/v2").setAllowedOrigins("*")
    }
}
