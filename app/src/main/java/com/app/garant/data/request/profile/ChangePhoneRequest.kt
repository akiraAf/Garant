package com.app.garant.data.request.profile

import com.google.gson.annotations.SerializedName

data class ChangePhoneRequest(
    @SerializedName("phone")
    val phone: Long
)