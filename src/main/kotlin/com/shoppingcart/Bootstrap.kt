package com.shoppingcart

import com.natpryce.konfig.*
import com.springernature.cnf.ConfigurationKeys.TIMEOUT
import com.springernature.cnf.ConfigurationKeys.TIME_TO_LIVE
import com.shoppingcart.common.http4k.Http4kFilters
import com.shoppingcart.common.http4k.AppAnatomyHttp4kHandlers
import com.shoppingcart.common.http4k.IncomingRequestLoggingFilter
import com.shoppingcart.common.logging.Logger
import com.shoppingcart.common.logging.Logging
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClients
import org.eclipse.jetty.server.Server
import org.http4k.client.ApacheClient
import org.http4k.core.*
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import java.net.URI
import java.time.Duration
import java.time.temporal.ChronoUnit

class Bootstrap(val logger: Logger = Logging("shoppingCart")) {
    val configuration: Configuration = EnvironmentVariables().overriding(defaultConfig())
    fun createHttp4kServer(app : HttpHandler, port : Int) : Http4kServer {
        val appAnatomy = AppAnatomyHttp4kHandlers.kroutons()
        val loggingFilter = IncomingRequestLoggingFilter(logger)
        val allRoutesHandler =
            loggingFilter.then(appAnatomy
                .apply { otherwise(app) }
                .toHandler())
        val jetty = Server(port)
        return Jetty(port, jetty).toServer(allRoutesHandler)
    }

    fun createHttp4kClient(baseUri: URI, bodyMode: BodyMode = BodyMode.Memory) : HttpHandler {

        val timeToLive: Duration = Duration.of(configuration.getOrElse(TIME_TO_LIVE,10), ChronoUnit.SECONDS)
        val timeout: Duration = Duration.of(configuration.getOrElse(TIMEOUT,10), ChronoUnit.SECONDS)
        val client = ApacheClient(
            with(HttpClients.custom()) {
                with(HttpClients.custom()) {
                    setDefaultRequestConfig(
                        with(RequestConfig.custom()) {
                            setConnectTimeout(timeout.toMillis().toInt())
                            setConnectionRequestTimeout(timeout.toMillis().toInt())
                            setRedirectsEnabled(false)
                            setCookieSpec(CookieSpecs.IGNORE_COOKIES)
                            build()
                        }
                    )
                }
                setConnectionTimeToLive(timeToLive.toNanos(), java.util.concurrent.TimeUnit.NANOSECONDS)
                setMaxConnTotal(100)
                evictExpiredConnections()
                build()
            }, bodyMode, bodyMode
        )

        val loggingFilter = IncomingRequestLoggingFilter(logger)

        return Http4kFilters.SetBaseUri(baseUri = baseUri).then(loggingFilter).then(client)
    }
}

fun defaultConfig() : Configuration{
    val ACCESS_CONTROL_SALT_TESTING by stringType
    val ACCESS_CONTROL_PASSWORD_HASH_TESTING by stringType
    return ConfigurationMap(
        Pair(ACCESS_CONTROL_PASSWORD_HASH_TESTING,"uag78UO7ag6BIY0gxsWJZDEbf9FQTZs0na+FPhJzMwE="),
        Pair(ACCESS_CONTROL_SALT_TESTING,"8klcvfC9w4xEFaxlwIbZjpYj03ujbt2ge+tBzrwWVN4=")
    )
}