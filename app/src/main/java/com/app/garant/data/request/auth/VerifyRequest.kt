package com.app.garant.data.request.auth

import com.google.gson.annotations.SerializedName

data class VerifyRequest(
    @SerializedName("phone")
    val phone: Long,
    @SerializedName("verify_code")
    val verify_code: Int
)