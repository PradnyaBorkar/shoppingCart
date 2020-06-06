package com.shoppingcart.common.logging

interface Logger {
    fun log(logDetails : LogDetails)
}

data class LogDetails(
    val level : LogLevel,
    val message : String,
    val exception : Throwable?
)

enum class LogLevel constructor(val toStdError: Boolean, val syslogSeverity: Int) {
    ERROR(true, 3),
    WARN(false, 4),
    INFO(false, 6),
    DEBUG(false, 7),
    TRACE(false, 7);

    fun atLeastAsSevereAs(other: LogLevel): Boolean {
        return ordinal <= other.ordinal
    }
}
