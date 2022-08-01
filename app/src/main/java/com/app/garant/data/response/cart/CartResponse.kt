package com.app.garant.data.response.cart

data class CartResponse(
    val count: Int,
    val id: Int,
    val month: Month,
    val monthly_price: Int,
    val products: List<Product>,
    val products_price: Int,
    val total_price: Double,
    val updated_at: String
)