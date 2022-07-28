package com.app.garant.presenter.viewModel.main

import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.product.ProductResponse
import kotlinx.coroutines.flow.Flow

interface MainScreenViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<ProductResponse>
    val tabСontentLoad: Flow<ArrayList<String>>
    val progressFlow: Flow<Boolean>
    val successSearch: Flow<ArrayList<String>>
    fun getProducts()
    fun getSearch(query: String)
    fun getNames()
    fun cancelProcess()
}