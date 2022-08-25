package ru.otus.otuskotlin.marketplace.serverlessapp.api.model

class Request {
    lateinit var path: String
    lateinit var httpMethod: String
    lateinit var headers: Map<String, String>
    lateinit var body: String
    var version: String? = null
    var resource: String? = null
    var multiValueHeaders: Map<String, List<String>>? = null
    var queryStringParameters: Map<String, String>? = null
    var requestContext: RequestContext? = null
    var pathParameters: Map<String, String>? = null
    var isBase64Encoded: Boolean = false
    var parameters: Map<String, String>? = null
    var multiValueParameters: Map<String, List<String>>? = null
    var operationId: String? = null
}

class RequestContext {
    lateinit var identity: Identity
    lateinit var httpMethod: String
    lateinit var requestId: String
    lateinit var requestTime: String
    var requestTimeEpoch: Long? = null
}

class Identity {
    lateinit var sourceIp: String
    lateinit var userAgent: String
}
