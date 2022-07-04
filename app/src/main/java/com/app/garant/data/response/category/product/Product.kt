package com.app.garant.data.response.category.product

data class Product(
    val available: Int,
    val id: Int,
    val image: String,
    val monthly_price: Int,
    val name: String,
    val price: Int
)