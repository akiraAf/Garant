package com.app.garant.presenter.viewModel.search

import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.search.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchProductsScreenViewModel {

    val successFlowSearch: Flow<SearchResponse>
    val errorFlow: Flow<String>
    val progressFlow: Flow<Boolean>

    fun getSearch(name: String)
}