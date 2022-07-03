package com.app.garant.presenter.viewModel.main

import com.app.garant.data.response.category.product.ProductResponse
import kotlinx.coroutines.flow.Flow

interface PopularViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<ProductResponse>
    val progressFlow: Flow<Boolean>

    fun getProducts()
}