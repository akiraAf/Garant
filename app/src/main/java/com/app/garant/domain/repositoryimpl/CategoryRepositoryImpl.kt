package com.app.garant.domain.repositoryimpl

import android.util.Log
import com.app.garant.data.api.CategoryApi
import com.app.garant.data.response.category.product.ProductCategoryResponse
import com.app.garant.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi
) : CategoryRepository {

    override fun getProducts(): Flow<Result<ProductCategoryResponse>> = flow {
        val response = api.getProduct()
        if (response.isSuccessful) {
            emit(Result.success<ProductCategoryResponse>(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)


}
