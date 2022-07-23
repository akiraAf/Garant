package com.app.garant.data.api

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.CartDeleteResponse
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.categories.CategoryResponse
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.search.SearchResponse
import com.app.garant.data.response.favorite.FavoriteResponse
import retrofit2.Response
import retrofit2.http.*

interface CategoryApi {

    @GET("compilation")
    suspend fun getProduct(): Response<ProductResponse>

    @GET("product")
    suspend fun getProductAll(@Query("filters[compilations.id]") id: Int): Response<AllProductsResponse>

    @GET("product")
    suspend fun getSearch(@Query("search[name]") name: String): Response<SearchResponse>

    @GET("category")
    suspend fun getCategory(): Response<CategoryResponse>

    @POST("cart")
    suspend fun addCart(@Body data: CartRequest): Response<CartResponse>

    @GET("cart")
    suspend fun getCart(): Response<CartResponse>

    @HTTP(method = "DELETE", path = "cart", hasBody = true)
    suspend fun deleteCart(@Body data: CartDeleteRequest): Response<CartDeleteResponse>

    @HTTP(method = "DELETE", path = "cart/drop", hasBody = true)
    suspend fun deleteAllCart(): Response<CartDeleteResponse>


    @POST("favorite")
    suspend fun addFavorite(@Body data: FavoriteRequest): Response<FavoriteResponse>

    @GET("favorite")
    suspend fun getFavorite(): Response<FavoriteResponse>

    @HTTP(method = "DELETE", path = "favorite", hasBody = true)
    suspend fun deleteFavorite(@Body data: FavoriteRequest): Response<FavoriteResponse>


}