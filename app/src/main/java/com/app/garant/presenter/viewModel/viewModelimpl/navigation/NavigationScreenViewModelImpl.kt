package com.app.garant.presenter.viewModel.viewModelimpl.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.navigation.NavigationScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationScreenViewModelImpl @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel(), NavigationScreenViewModel {


    override val successFlow = eventValueFlow<Int>()
    override val errorFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()

    override fun getAmount() {
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlow.emit(true)
        }

        categoryRepository.getCart().onEach {
            it.onSuccess { data ->
                progressFlow.emit(false)
                successFlow.emit(data.count)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }
}