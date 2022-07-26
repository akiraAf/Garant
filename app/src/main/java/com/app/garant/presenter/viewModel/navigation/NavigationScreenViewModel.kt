package com.app.garant.presenter.viewModel.navigation

import com.app.garant.data.response.category.allProducts.AllProductsResponse
import kotlinx.coroutines.flow.Flow

interface NavigationScreenViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<Int>
    val progressFlow: Flow<Boolean>

    fun getAmount()
}