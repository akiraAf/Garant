package com.app.garant.models

data class PaymentInfo(
    val number: String,
    val date: String,
    val cost: String,
    val remainder: String,
    val status: Int
)
