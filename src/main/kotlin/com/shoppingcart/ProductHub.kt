package com.shoppingcart

import com.shoppingcart.domain.Product
import com.shoppingcart.domain.ProductId
import com.shoppingcart.functional.ErrorCode
import com.shoppingcart.functional.Result
import com.shoppingcart.functional.Result.Companion.asSuccess
import java.math.BigDecimal

interface ProductHub {
    fun fetchAllProducts() : Result<ErrorCode,List<Product>>
    fun add(product: Product) : Result<ErrorCode,ProductId>
}

class DefaultProductHub: ProductHub{

    private val products = mutableListOf(
        Product(ProductId(1),"Bag", BigDecimal("500.00")),
        Product(ProductId(2),"Shoe", BigDecimal("200.00")))

    override fun fetchAllProducts(): Result<ErrorCode, List<Product>> {
        return Result.Success(products)
    }

    override fun add(product: Product): Result<ErrorCode, ProductId> {
         products.add(product)
         return product.id.asSuccess()
    }
}