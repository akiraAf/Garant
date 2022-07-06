package com.app.garant.data.response.category

data class Data(
    val available: Int,
    val discount_percentage: Int,
    val discount_price: Any,
    val id: Int,
    val image: String,
    val monthly_price: Int,
    val name: String,
    val price: Int
)