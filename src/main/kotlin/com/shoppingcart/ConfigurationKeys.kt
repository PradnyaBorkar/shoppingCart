package com.springernature.cnf

import com.natpryce.konfig.getValue
import com.natpryce.konfig.intType
import com.natpryce.konfig.longType
import com.natpryce.konfig.stringType

object ConfigurationKeys{
    val TIME_TO_LIVE by longType
    val TIMEOUT by longType
    val SPARKPOST_BASE_URI by stringType
    val SPARKPOST_API_KEY by stringType
    val APP_ENV by stringType
    val SLACK_BASE_URL by stringType
    val SLACK_CHANNEL by stringType
    val SLACK_TOKEN by stringType
    val PAYMENT_DB_HOST by stringType
    val PAYMENT_DB_PORT by intType
    val PAYMENT_DB_SCHEMA by stringType
    val PAYMENT_DB_USERNAME by stringType
    val PAYMENT_DB_PASSWORD by stringType

}