package com.app.garant.data.response.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val available: Int,
    val count: Int,
    val discount_percentage: Int,
    val discount_price: Int,
    val id: Int,
    val image: String,
    val is_cart: Int,
    val is_favorite: Int,
    val monthly_price: Int,
    val name: String,
    val price: Int
) : Parcelable