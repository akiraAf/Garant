package com.app.garant.data.api

import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.categories.CategoryResponse
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryApi {

    @GET("compilation")
    suspend fun getProduct(): Response<ProductResponse>

    @GET("product")
    suspend fun getProductAll(@Query("filters[compilations.id]") id: Int): Response<AllProductsResponse>

    @GET("product")
    suspend fun getSearch(@Query("search[name]") name: String): Response<SearchResponse>

    @GET("category")
    suspend fun getCategory(): Response<CategoryResponse>
}