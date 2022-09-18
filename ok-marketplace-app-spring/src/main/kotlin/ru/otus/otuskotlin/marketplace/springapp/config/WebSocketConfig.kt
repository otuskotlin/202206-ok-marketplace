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

    private val sessions = mutableMapOf<String, SpringWsSession>()

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(WsAdHandlerV1(processor, sessions), "/ws/v1").setAllowedOrigins("*")
        registry.addHandler(WsAdHandlerV2(processor, sessions), "/ws/v2").setAllowedOrigins("*")
    }
}
