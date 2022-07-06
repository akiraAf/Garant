package com.app.garant.domain.repository

import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.categories.CategoryResponse
import com.app.garant.data.response.category.product.ProductResponse
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getProducts(): Flow<Result<ProductResponse>>
    fun collectCompanions(): ArrayList<String>
    fun getProductByCompanion(name: String): Flow<Result<ProductResponse>>
    fun getAllProducts(id: Int): Flow<Result<AllProductsResponse>>
    fun getCategory(): Flow<Result<CategoryResponse>>

}
