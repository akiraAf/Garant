package com.app.garant.data.request.auth

data class VerifyRequest(
    val phone: Long,
    val verify_code: Int
)