package com.app.garant.data.api

import com.app.garant.data.response.category.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {

    @GET("compilation")
    suspend fun getProduct(): Response<ProductResponse>

}