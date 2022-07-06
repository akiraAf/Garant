package com.app.garant.presenter.viewModel.main

import com.app.garant.data.response.category.product.ProductResponseItem
import kotlinx.coroutines.flow.Flow

interface TopSellingPageViewModel {
    val errorFlowProduct: Flow<String>
    val successFlowProduct: Flow<ArrayList<ProductResponseItem>>
    val progressFlowProduct: Flow<Boolean>

    fun getProducts()
}