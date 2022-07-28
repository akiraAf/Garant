package com.app.garant.data.request.order

data class OrderCreateRequest(
    val address: Address,
    val contact: Contact,
    val delivery_type: String,
    val payment_type: String,
    val products: List<Product>
)