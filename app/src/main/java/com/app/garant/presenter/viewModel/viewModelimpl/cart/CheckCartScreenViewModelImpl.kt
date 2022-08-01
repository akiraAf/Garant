package com.app.garant.presenter.viewModel.viewModelimpl.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.cart.Product
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.cart.CheckCartViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CheckCartScreenViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel(), CheckCartViewModel {


    override val successFlow = eventValueFlow<CartResponse>()
    override val errorFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()

    override val successFlowAv = eventValueFlow<ArrayList<Product>>()
    override val errorFlowAv = eventValueFlow<String>()
    override val progressFlowAv = eventValueFlow<Boolean>()

    private val available = ArrayList<Product>()


    private var getCartJob: Job? = null
    override fun getCart() {
        if (!isConnected()) {
            return
        }
        getCartJob?.cancel()
        getCartJob = categoryRepository.getCart().onEach {
            available.clear()
            it.onSuccess {
                progressFlow.emit(false)
                progressFlowAv.emit(false)
                for (i in it.products) {
                    if (i.available == 1) {
                        available.add(i)
                    }
                }
                successFlowAv.emit(available)
                successFlow.emit(it)
            }
            it.onFailure { throwable ->
                progressFlowAv.emit(false)
                progressFlow.emit(false)
                errorFlowAv.emit(throwable.message.toString())
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}