package ru.otus.otuskotlin.marketplace.springapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.marketplace.biz.MkplAdProcessor

@Configuration
class ServiceConfig {
    @Bean
    fun processor(): MkplAdProcessor = MkplAdProcessor()

}
