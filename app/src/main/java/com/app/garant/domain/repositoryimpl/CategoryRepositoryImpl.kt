package com.app.garant.domain.repositoryimpl

import com.app.garant.data.api.CategoryApi
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.categories.CategoryResponse
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.data.response.category.search.SearchResponse
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

    private var productCatalogResponse: ProductResponse? = null
    private var companionsNames = ArrayList<String>()


    override fun getProducts(): Flow<Result<ProductResponse>> = flow {
        val response = api.getProduct()
        if (response.isSuccessful) {
            productCatalogResponse = response.body()!!
            emit(Result.success<ProductResponse>(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)

    override fun collectCompanions(): ArrayList<String> {
        if (productCatalogResponse != null) {
            companionsNames.clear()
            companionsNames.reverse()
            for (i in 0 until productCatalogResponse!!.size) {
                companionsNames.add(productCatalogResponse!![i].name)
            }
        }
        return companionsNames
    }

    override fun getProductByCompanion(name: String): Flow<Result<ProductResponse>> = flow {
        if (productCatalogResponse == null) {
            val response = api.getProduct()
            if (response.isSuccessful) {
                productCatalogResponse = response.body()!!
            }
        }
        var list = ArrayList<ProductResponseItem>()
        if (productCatalogResponse != null) {
            for (i in 0 until productCatalogResponse!!.size) {
                if (productCatalogResponse!![i].name == name) {
                    list.add(productCatalogResponse!![i])
                }
            }
        }
        emit(Result.success(productCatalogResponse) as Result<ProductResponse>)
    }

    override fun getAllProducts(id: Int): Flow<Result<AllProductsResponse>> = flow {

        val response = api.getProductAll(id)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)

    override fun getCategory(): Flow<Result<CategoryResponse>> = flow {
        val response = api.getCategory()
        if (response.isSuccessful) {
            emit(Result.success((response.body()!!)))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)

    override fun getSearch(name: String): Flow<Result<SearchResponse>> = flow {
        val response = api.getSearch(name)
        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            emit(Result.failure(Throwable(response.errorBody().toString())))
        }
    }.catch {
        emit(Result.failure(Throwable(it.message)))
    }.flowOn(Dispatchers.IO)

}
