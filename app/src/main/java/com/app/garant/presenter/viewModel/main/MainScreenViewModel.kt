package com.app.garant.presenter.viewModel.main

import com.app.garant.data.response.category.product.ProductResponse
import kotlinx.coroutines.flow.Flow

interface MainScreenViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<ProductResponse>
    val tabСontentLoad: Flow<ArrayList<String>>
    val progressFlow: Flow<Boolean>
    fun getProducts()
    fun getNames()
}