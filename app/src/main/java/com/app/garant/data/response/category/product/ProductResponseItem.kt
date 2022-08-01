package com.app.garant.data.response.category.product

import com.app.garant.data.response.cart.Product

data class ProductResponseItem(
    val id: Int,
    val name: String,
    val products: List<Product>
)