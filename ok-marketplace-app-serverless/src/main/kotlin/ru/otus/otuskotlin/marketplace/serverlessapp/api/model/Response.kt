package ru.otus.otuskotlin.marketplace.serverlessapp.api.model

data class Response(
    val status: Int,
    val headers: Map<String, String>,
    val body: String
)