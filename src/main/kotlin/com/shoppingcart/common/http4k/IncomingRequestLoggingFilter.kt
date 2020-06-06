package com.shoppingcart.common.http4k

import com.shoppingcart.common.logging.LogDetails
import com.shoppingcart.common.logging.LogLevel
import com.shoppingcart.common.logging.Logger
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import java.time.Clock
import java.time.Duration
import java.time.Instant

class IncomingRequestLoggingFilter(
    private val logger: Logger,
    private val clock: Clock = Clock.systemUTC()
) : Filter {
    override fun invoke(next: HttpHandler): HttpHandler = { request ->
        audit(
            clock = clock,
            action = { next(request) },
            onComplete = { duration, response ->
                logger.log(
                    LogDetails(
                        LogLevel.INFO,
                        "Request = ${request.toSummary()}, Response = ${response.toSummary()} Time Taken = $duration",
                        null
                    )
                )
            }
        )
    }
}


fun audit(
    clock: Clock = Clock.systemUTC(),
    action: () -> Response,
    onComplete: (Duration, Response) -> Unit
): Response {
    val start = Instant.now(clock)
    val actionOutcome = action()
    val stop = Instant.now(clock)
    onComplete(Duration.between(start,stop),actionOutcome)
    return actionOutcome
}


