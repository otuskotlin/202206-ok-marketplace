package ru.otuskotlin.m3l1.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class M3l1SpringApplication

fun main(args: Array<String>) {
    val ctx = runApplication<M3l1SpringApplication>(*args)
    println("""
        
        count = ${ctx.beanDefinitionCount}
    """.trimIndent())
}
