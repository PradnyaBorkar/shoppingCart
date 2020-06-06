package com.shoppingcart.common.logging

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.slf4j.LoggerFactory

class Logging(name : String) : Logger {

    private val logger = LoggerFactory.getLogger(name)

    override fun log(logDetails: LogDetails) {
        when(logDetails.level){
            LogLevel.ERROR -> logger.error(logDetails.message,logDetails.exception)
            LogLevel.WARN -> logger.warn(logDetails.message)
            LogLevel.INFO -> logger.info(logDetails.message)
            LogLevel.DEBUG -> logger.debug(logDetails.message)
            LogLevel.TRACE -> logger.trace(logDetails.message)
        }
    }
}

val objectMapper : ObjectMapper = ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)

fun Exception.toLogDetails(level : LogLevel = LogLevel.ERROR) : LogDetails {
    return LogDetails(
        level,
        objectMapper.writeValueAsString("Exception occurred"),
        this
    )
}
