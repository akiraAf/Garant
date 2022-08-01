package com.app.garant.data.response.category.allProducts

import com.app.garant.data.response.cart.Product

data class AllProductsResponse(
    val current_page: Int,
    val `data`: List<Product>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val links: List<Link>,
    val next_page_url: Any,
    val path: String,
    val per_page: Int,
    val prev_page_url: Any,
    val to: Int,
    val total: Int
)