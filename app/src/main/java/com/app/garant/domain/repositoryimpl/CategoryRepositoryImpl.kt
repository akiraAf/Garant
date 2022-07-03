package com.app.garant.domain.repositoryimpl

import com.app.garant.data.api.AuthApi
import com.app.garant.data.api.CategoryApi
import com.app.garant.data.pref.MyPref
import com.app.garant.data.response.auth.LoginResponse
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: CategoryApi,
    private val pref: MyPref
) : CategoryRepository {

    override fun getProducts(): Flow<Result<ProductResponse>> = flow {
        val response = api.getProduct()
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)



}
