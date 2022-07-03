package com.app.garant.domain.repository

import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getProducts(): Flow<Result<ProductResponse>>

}
