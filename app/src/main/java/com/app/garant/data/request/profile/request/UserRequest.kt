package com.app.garant.data.request.profile.request

data class UserRequest(
    val address: String,
    val company: String,
    val district_id: Int,
    val phone: Long,
    val profession_id: Int
)