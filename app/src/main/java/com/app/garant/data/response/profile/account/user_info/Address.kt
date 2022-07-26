package com.app.garant.data.response.profile.account.user_info

data class Address(
    val address: String,
    val city: City,
    val district: District,
    val region: Region
)