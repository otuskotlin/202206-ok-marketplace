package ru.otus.otuskotlin.marketplace.springapp.config

import org.springdoc.core.SpringDocConfigProperties
import org.springdoc.core.SpringDocConfiguration
import org.springdoc.core.providers.ObjectMapperProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfiguration {
    @Bean
    fun springDocConfiguration() = SpringDocConfiguration()

    @Bean
    fun springDocConfigProperties() = SpringDocConfigProperties()

    @Bean
    fun objectMapperProvider(springDocConfigProperties: SpringDocConfigProperties) =
        ObjectMapperProvider(springDocConfigProperties)
}