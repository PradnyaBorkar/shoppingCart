package com.shoppingcart

import com.natpryce.krouton.http4k.resources
import com.shoppingcart.Routes.fetchAll
import com.shoppingcart.common.logging.Logging
import com.shoppingcart.domain.Product
import com.shoppingcart.domain.ProductId
import org.http4k.core.*
import java.math.BigDecimal

class ShoppingCartHandler() : HttpHandler {

    private val products = listOf(
        Product(ProductId(1),"Bag", BigDecimal("500.00")),
        Product(ProductId(2),"Shoe", BigDecimal("200.00")))

    private val httpHandler = resources {

     fetchAll methods {
        Method.GET {
            Response(Status.OK).body(products.toString())
        }
     }


    }

    override fun invoke(request: Request): Response {
        return httpHandler(request)
    }

}
