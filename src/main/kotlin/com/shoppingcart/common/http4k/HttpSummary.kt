package com.shoppingcart.common.http4k

import org.http4k.core.*


data class RequestSummary(
    val uri : Uri,
    val method : Method,
    val headers : Headers,
    val bodyString : String
)

data class ResponseSummary(
    val statusCode : Int,
    val headers : Headers,
    val bodyString : String
)

fun Response.toSummary() =
    ResponseSummary(
        this.status.code,
        this.headers.safeHeaders(),
        this.bodyString()
    )

fun Headers.safeHeaders(): Headers {
    return this.filter { it.first != org.apache.http.HttpHeaders.AUTHORIZATION }
}
fun Request.toSummary() =
    RequestSummary(
        this.uri,
        this.method,
        this.headers.safeHeaders(),
        this.bodyString()
    )