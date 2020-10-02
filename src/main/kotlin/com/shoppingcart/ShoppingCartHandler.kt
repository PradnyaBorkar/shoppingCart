package com.shoppingcart

import com.natpryce.krouton.http4k.resources
import com.shoppingcart.Routes.add
import com.shoppingcart.Routes.fetchAll
import com.shoppingcart.domain.toJson
import com.shoppingcart.functional.Result.Companion.map
import com.shoppingcart.functional.Result.Companion.orElse
import org.http4k.core.*

class ShoppingCartHandler(private val productHub: ProductHub) : HttpHandler {

    private val httpHandler = resources {

         fetchAll methods {
            Method.GET {
                productHub.fetchAllProducts()
                    .map { Response(Status.OK).body(it.toString()) }
                    .orElse { Response(Status.NOT_FOUND).body("No product found") }
            }
         }

        add methods {
            Method.POST { request ->
                request.bodyString().toJson().map {
                    Response(Status.OK)
                }.orElse {
                    Response(Status.INTERNAL_SERVER_ERROR).body(it.message)
                }
            }
        }
    }

    override fun invoke(request: Request): Response {
        return httpHandler(request)
    }

}




