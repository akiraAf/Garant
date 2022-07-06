package com.app.garant.presenter.viewModel.catolog


import com.app.garant.data.response.category.categories.CategoryResponse
import kotlinx.coroutines.flow.Flow

interface CategoryViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<CategoryResponse>
    val progressFlow: Flow<Boolean>
    fun getCategory()
}