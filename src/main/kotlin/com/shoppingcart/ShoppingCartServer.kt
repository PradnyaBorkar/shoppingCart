package com.shoppingcart

import com.shoppingcart.common.logging.Logging

fun main(args: Array<String>){
    val logger = Logging("shoppingCart")
    val bootstrap = Bootstrap(logger)
    val configuration = bootstrap.configuration

    val app = ShoppingCartHandler(logger)
    val server = bootstrap.createHttp4kServer(app,8080)
    server.start()
}