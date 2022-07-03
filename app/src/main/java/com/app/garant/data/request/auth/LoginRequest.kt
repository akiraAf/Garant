package com.app.garant.data.request.auth

import com.google.gson.annotations.SerializedName


data class LoginRequest(
    @SerializedName("phone")
    val phone: Long
)
