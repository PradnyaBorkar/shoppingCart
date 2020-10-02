package com.shoppingcart

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.shoppingcart.domain.Product
import com.shoppingcart.domain.ProductId
import org.junit.Test
import java.math.BigDecimal

class ProductHubTest {

    @Test
    internal fun `should add a product to list`() {
        val product = Product(id = ProductId(4),name = "Purse",price = BigDecimal(1200))
       // val productId = DefaultProductHub().add(product).expectSuccess()
       // assertThat(productId, equalTo())
    }
}