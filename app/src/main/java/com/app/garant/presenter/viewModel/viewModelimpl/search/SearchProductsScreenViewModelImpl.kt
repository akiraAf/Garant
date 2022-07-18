package com.app.garant.presenter.viewModel.viewModelimpl.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.search.SearchResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.search.SearchProductsScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchProductsScreenViewModelImpl @Inject constructor(private val categoryRepository: CategoryRepository) :
    SearchProductsScreenViewModel, ViewModel() {

    override val errorFlow = eventValueFlow<String>()
    override val successFlowSearch = eventValueFlow<SearchResponse>()
    override val progressFlow = eventValueFlow<Boolean>()


    override fun getSearch(name: String) {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        categoryRepository.getSearch(name).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowSearch.emit(it)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }
}