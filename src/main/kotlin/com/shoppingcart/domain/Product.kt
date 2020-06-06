package com.shoppingcart.domain

import java.math.BigDecimal

data class Product(val id : ProductId,val name: String, val price : BigDecimal)

data class ProductId(val id : Int)
