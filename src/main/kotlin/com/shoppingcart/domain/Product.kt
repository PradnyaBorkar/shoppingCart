package com.shoppingcart.domain

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.shoppingcart.functional.ErrorCode
import com.shoppingcart.functional.Result
import com.shoppingcart.functional.Result.Companion.asFailure
import com.shoppingcart.functional.Result.Companion.asSuccess
import java.math.BigDecimal

data class Product(val id : ProductId,val name: String, val price : BigDecimal)

data class ProductId(val id : Int)

fun String.toJson() : Result<ParsingException, JsonNode> {
    val mapper = ObjectMapper()
    return try {
        mapper.readTree(this).asSuccess()
    } catch (throwable : Throwable){
        ParsingException("Failed to parse product $this with exception : ${throwable.message}").asFailure()
    }
}

data class ParsingException(val message : String) : ErrorCode

