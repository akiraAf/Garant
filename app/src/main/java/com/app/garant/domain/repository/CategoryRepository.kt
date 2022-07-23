package com.app.garant.domain.repository

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
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getProducts(): Flow<Result<ProductResponse>>

    fun collectCompanions(): ArrayList<String>

    fun getProductByCompanion(name: String): Flow<Result<ProductResponse>>

    fun getAllProducts(id: Int): Flow<Result<AllProductsResponse>>

    fun getCategory(): Flow<Result<CategoryResponse>>

    fun getSearch(name: String): Flow<Result<SearchResponse>>

    fun addCart(request: CartRequest): Flow<Result<CartResponse>>

    fun getCart(): Flow<Result<CartResponse>>

    fun deleteCart(request: CartDeleteRequest): Flow<Result<CartDeleteResponse>>

    fun deleteAllCart(): Flow<Result<CartDeleteResponse>>

    fun addFavorite(request: FavoriteRequest): Flow<Result<FavoriteResponse>>

    fun getFavorite(): Flow<Result<FavoriteResponse>>

    fun deleteFavorite(request: FavoriteRequest): Flow<Result<FavoriteResponse>>

}
