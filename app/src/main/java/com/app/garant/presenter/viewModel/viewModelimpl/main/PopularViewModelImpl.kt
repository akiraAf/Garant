package com.app.garant.presenter.viewModel.viewModelimpl.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.api.CategoryApi
import com.app.garant.data.request.profile.UpdatePhoneRequest
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.profile.UpdatePhoneResponce
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.main.PopularViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModelImpl @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel(), PopularViewModel {

    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<ProductResponse>()
    override val progressFlow = eventValueFlow<Boolean>()

    override fun getProducts() {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }

        categoryRepository.getProducts().onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlow.emit(it)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

}