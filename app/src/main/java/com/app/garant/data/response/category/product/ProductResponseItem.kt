package com.app.garant.data.response.category.product

import com.app.garant.data.response.category.Data

data class ProductResponseItem(
    val id: Int,
    val name: String,
    val products: List<Data>
)