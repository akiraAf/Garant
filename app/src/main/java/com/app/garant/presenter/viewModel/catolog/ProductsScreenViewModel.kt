package com.app.garant.presenter.viewModel.catolog

import com.app.garant.data.response.category.allProducts.AllProductsResponse
import kotlinx.coroutines.flow.Flow


interface ProductsScreenViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<AllProductsResponse>
    val progressFlow: Flow<Boolean>
    fun getAllProducts(id: Int)
}