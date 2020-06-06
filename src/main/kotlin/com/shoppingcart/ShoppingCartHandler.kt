package com.shoppingcart

import com.natpryce.krouton.http4k.resources
import com.shoppingcart.Routes.fetchAll
import com.shoppingcart.common.logging.Logging
import org.http4k.core.*

class ShoppingCartHandler(logger: Logging) : HttpHandler {

    private val httpHandler = resources {

     fetchAll methods {
        Method.GET {
            Response(Status.OK).body("Ok")
        }
     }


    }

    override fun invoke(request: Request): Response {
        return httpHandler(request)
    }

}
