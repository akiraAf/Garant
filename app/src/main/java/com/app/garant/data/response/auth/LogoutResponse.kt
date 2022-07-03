package com.app.garant.data.response.auth

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("context")
    val context: List<Any>,
    @SerializedName("message")
    val message: String
)