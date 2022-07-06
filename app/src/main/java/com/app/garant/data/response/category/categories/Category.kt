package com.app.garant.data.response.category.categories

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Category(
 //   val categories: List<Any>,
    val id: Int,
    val image: String,
    val name: String
):Parcelable