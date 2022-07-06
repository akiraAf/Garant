package com.app.garant.data.response.category.categories

data class CategoryResponseItem(
    val categories: List<Category>,
    val id: Int,
    val image: String,
    val name: String
)