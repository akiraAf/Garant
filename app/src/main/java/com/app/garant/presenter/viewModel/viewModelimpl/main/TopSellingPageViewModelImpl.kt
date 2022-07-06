package com.app.garant.presenter.viewModel.viewModelimpl.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.other.StaticValue
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.screens.main.TopSellingPage
import com.app.garant.presenter.viewModel.main.TopSellingPageViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopSellingPageViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel(), TopSellingPageViewModel {

    override val successFlowProduct = eventValueFlow<ArrayList<ProductResponseItem>>()
    override val progressFlowProduct = eventValueFlow<Boolean>()
    override val errorFlowProduct = eventValueFlow<String>()

    override fun getProducts() {
        if (!isConnected()) {
            return
        }
        categoryRepository.getProductByCompanion(StaticValue.nameCategory).onEach {
            it.onSuccess {
                progressFlowProduct.emit(false)
                successFlowProduct.emit(it)
            }
            it.onFailure { throwable ->
                progressFlowProduct.emit(false)
                errorFlowProduct.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }
}