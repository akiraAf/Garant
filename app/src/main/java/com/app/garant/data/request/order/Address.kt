package com.app.garant.data.request.order

data class Address(
    val address: String,
    val district_id: Int,
    val latitude: Double,
    val longitude: Double
)