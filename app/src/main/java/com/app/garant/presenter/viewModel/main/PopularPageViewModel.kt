package com.app.garant.presenter.viewModel.main

import com.app.garant.data.response.category.product.ProductResponse
import kotlinx.coroutines.flow.Flow

interface PopularPageViewModel {
    val errorFlowProduct: Flow<String>
    val successFlowProduct: Flow<ProductResponse>
    val progressFlowProduct: Flow<Boolean>

    fun getProducts()
}