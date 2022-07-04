package com.app.garant.domain.repository

import com.app.garant.data.response.category.product.ProductCategoryResponse
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getProducts(): Flow<Result<ProductCategoryResponse>>

}
