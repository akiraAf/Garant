package com.app.garant.data.response.category.product

data class ProductCategoryResponseItem(
    val id: Int,
    val name: String,
    val products: List<Product>
)