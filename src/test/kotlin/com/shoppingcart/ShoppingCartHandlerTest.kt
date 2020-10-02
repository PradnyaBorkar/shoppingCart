package com.shoppingcart

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.shoppingcart.domain.Product
import com.shoppingcart.domain.ProductId
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.Ignore
import org.junit.Test
import java.math.BigDecimal

class ShoppingCartHandlerTest {

    @Test
    fun `should return the list of products`() {
        val request = Request(Method.GET,"http://localhost:8080/products")
        httpHandler.invoke(request).let { response ->
            assertThat(response.status, equalTo(Status.OK))
            val expectedProducts =  listOf(
            Product(ProductId(1),"Bag", BigDecimal("500.00")),
            Product(ProductId(2),"Shoe", BigDecimal("200.00"))
        )
            assertThat(response.body.toString(), equalTo(expectedProducts.toString()))
          }
      }

    @Test
    @Ignore
    fun `should return the status NOT_FOUND when no product found`() {
        val request = Request(Method.GET,"http://localhost:8080/products")
        assertThat(httpHandler.invoke(request).status, equalTo(Status.NOT_FOUND))
    }

    @Test
    fun `should add a product to the list`() {
       val request = Request(Method.POST,"http://localhost:8080/add").body("""{
            "id" : "3",
            "name" : "TShirt",
            "price" : "750.00"
        }
        """.trimIndent()
                )
        assertThat(httpHandler.invoke(request).status, equalTo(Status.OK))
    }

    @Test
    fun `should throw parseException when parsing fails while adding product`() {
        val request = Request(Method.POST,"http://localhost:8080/add").body("""{
            "id" : "3"
            "name" : "TShirt",
            "price" : "750.00"
        }
        """.trimIndent()
        )
        assertThat(httpHandler.invoke(request).status, equalTo(Status.INTERNAL_SERVER_ERROR))
    }

    companion object{
       val httpHandler = ShoppingCartHandler(DefaultProductHub())
    }
}