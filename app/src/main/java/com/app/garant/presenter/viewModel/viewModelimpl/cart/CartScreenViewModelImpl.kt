package com.app.garant.presenter.viewModel.viewModelimpl.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.response.cart.CartDeleteResponse
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.domain.repository.UserRepository
import com.app.garant.presenter.viewModel.cart.CartViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel(), CartViewModel {


    override val successFlowCart = eventValueFlow<CartResponse>()
    override val successFlowDelete = eventValueFlow<Unit>()
    override val progressFlowProduct = eventValueFlow<Boolean>()
    override val errorFlowProduct = eventValueFlow<String>()
    override val errorFlowDelete = eventValueFlow<String>()


    override fun getCart() {
        if (!isConnected()) {
            return
        }

        categoryRepository.getCart().onEach {
            it.onSuccess {
                progressFlowProduct.emit(false)
                successFlowCart.emit(it)
            }
            it.onFailure { throwable ->
                progressFlowProduct.emit(false)
                errorFlowProduct.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


    override fun deleteCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }
        categoryRepository.deleteCart(request).onEach {
            it.onSuccess {
                progressFlowProduct.emit(false)
                successFlowDelete.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowProduct.emit(false)
                errorFlowDelete.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun deleteAllCart() {
        if (!isConnected()) {
            return
        }
        categoryRepository.deleteAllCart().onEach {
            it.onSuccess {
                progressFlowProduct.emit(false)
                successFlowDelete.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowProduct.emit(false)
                errorFlowDelete.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}