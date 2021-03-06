package com.app.garant.domain.repository

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartMonthRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.branch.BranchResponse
import com.app.garant.data.response.brand.BrandResponse
import com.app.garant.data.response.cart.CartDeleteResponse
import com.app.garant.data.response.cart.EmptyResponse
import com.app.garant.data.response.cart.CartParchRequest
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

    fun addCart(request: CartRequest): Flow<Result<EmptyResponse>>

    fun getCart(): Flow<Result<CartResponse>>

    fun putCartMonth(request: CartMonthRequest): Flow<Result<Unit>>

    fun patchCart(request: CartParchRequest): Flow<Result<Unit>>

    fun deleteCart(request: CartDeleteRequest): Flow<Result<CartDeleteResponse>>

    fun deleteAllCart(): Flow<Result<CartDeleteResponse>>

    fun addFavorite(request: FavoriteRequest): Flow<Result<FavoriteResponse>>

    fun getFavorite(): Flow<Result<FavoriteResponse>>

    fun getBrand(): Flow<Result<BrandResponse>>

    fun deleteFavorite(request: FavoriteRequest): Flow<Result<FavoriteResponse>>

    fun getBranch(): Flow<Result<BranchResponse>>

    fun filterDiscountPercentage(
        compilations_id: Int,
        discount_percentage_id: Int
    ): Flow<Result<AllProductsResponse>>
}
