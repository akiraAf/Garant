package com.app.garant.data.response.profile.account.user_info

data class UserInfoResponse(
    val address: Address,
    val contacts: Contacts,
    val documents: Documents,
    val id: Int,
    val profession: Profession
)