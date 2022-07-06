package com.app.garant.presenter.viewModel.viewModelimpl.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.categories.CategoryResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.catolog.CategoryViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModelImpl @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel(), CategoryViewModel {

    override val successFlow = eventValueFlow<CategoryResponse>()
    override val errorFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()

    override fun getCategory() {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }

        categoryRepository.getCategory().onEach {
            it.onSuccess { data ->
                progressFlow.emit(false)
                successFlow.emit(data)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }
}