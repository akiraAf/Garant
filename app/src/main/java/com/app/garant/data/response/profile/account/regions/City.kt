package com.app.garant.data.response.profile.account.regions

data class City(
    val districts: List<District>,
    val id: Int,
    val name: String
)