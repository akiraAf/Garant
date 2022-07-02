package com.app.garant.data.request.profile

data class UpdatePhoneRequest(
    val phone: Long,
    val verify_code: Int
)