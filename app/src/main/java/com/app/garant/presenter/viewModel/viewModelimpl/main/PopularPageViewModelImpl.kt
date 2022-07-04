package com.app.garant.presenter.viewModel.viewModelimpl.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.category.product.ProductCategoryResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.main.PopularPageViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularPageViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) :
    ViewModel(), PopularPageViewModel {

    override val successFlowProduct = eventValueFlow<ProductCategoryResponse>()
    override val progressFlowProduct = eventValueFlow<Boolean>()
    override val errorFlowProduct = eventValueFlow<String>()

    override fun getProducts() {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlowProduct.emit(true)
        }

        categoryRepository.getProducts().onEach {
            it.onSuccess { products->
                progressFlowProduct.emit(false)
                successFlowProduct.emit(products)
            }
            it.onFailure { throwable ->
                progressFlowProduct.emit(false)
                errorFlowProduct.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}