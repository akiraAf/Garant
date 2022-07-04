package com.app.garant.presenter.viewModel.main

import com.app.garant.data.response.category.product.ProductCategoryResponse
import kotlinx.coroutines.flow.Flow

interface PopularPageViewModel {
    val errorFlowProduct: Flow<String>
    val successFlowProduct: Flow<ProductCategoryResponse>
    val progressFlowProduct: Flow<Boolean>

    fun getProducts()
}