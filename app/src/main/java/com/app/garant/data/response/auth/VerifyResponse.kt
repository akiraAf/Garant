package com.app.garant.data.response.auth

import com.google.gson.annotations.SerializedName

data class VerifyResponse(
    @SerializedName("token")
    val token: String
)