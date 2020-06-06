package com.shoppingcart.common.http4k

import org.http4k.core.Filter
import java.net.URI

object Http4kFilters {
    object SetBaseUri {
        operator fun invoke(baseUri: URI): Filter = Filter { next ->
            { request ->
                next(
                    request
                        .uri(request.uri.copy(
                            scheme = baseUri.scheme.orEmpty(),
                            userInfo = baseUri.userInfo.orEmpty(),
                            host = baseUri.host.orEmpty(),
                            port = baseUri.port.takeUnless { it == - 1 },
                            path = baseUri.path.orEmpty().removeSuffix("/") + request.uri.path
                        ))
                )
            }
        }
    }
}
