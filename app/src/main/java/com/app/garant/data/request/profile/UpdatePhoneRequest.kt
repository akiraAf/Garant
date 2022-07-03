package com.app.garant.data.request.profile

import com.google.gson.annotations.SerializedName

data class UpdatePhoneRequest(
    @SerializedName("phone")
    val phone: Long,
    @SerializedName("verify_code")
    val verify_code: Int
)