package com.app.garant.data.response.cart

data class CartResponse(
    val count: Int,
    val id: Int,
    val month: Month,
    val monthly_price: Int,
    val products: List<Product>,
    val total_price: Int,
    val updated_at: String
)