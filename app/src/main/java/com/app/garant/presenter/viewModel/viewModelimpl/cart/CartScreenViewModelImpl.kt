package com.app.garant.presenter.viewModel.viewModelimpl.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartMonthRequest
import com.app.garant.data.response.cart.CartDeleteResponse
import com.app.garant.data.response.cart.CartParchRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.cart.Product
import com.app.garant.data.response.category.Data
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

    override val successFlowCartAvailable = eventValueFlow<ArrayList<Product>>()
    override val successFlowCart = eventValueFlow<CartResponse>()
    override val successFlowCartUnavailable = eventValueFlow<ArrayList<Product>>()
    override val successFlowDelete = eventValueFlow<Unit>()
    override val successFlowDeleteAvailable = eventValueFlow<Unit>()

    override val errorFlowProduct = eventValueFlow<String>()
    override val errorFlowDelete = eventValueFlow<String>()
    override val errorFlowDeleteAvailable = eventValueFlow<String>()
    private val available = ArrayList<Product>()
    private val unavailable = ArrayList<Product>()

    override val progressFlowProduct = eventValueFlow<Boolean>()
    override val progressFlowProductAv = eventValueFlow<Boolean>()
    override val progressFlowProductUn = eventValueFlow<Boolean>()
    override val progressFlowDelete = eventValueFlow<Boolean>()

    override val successPut = eventValueFlow<String>()
    override val errorPut = eventValueFlow<String>()
    override val progressPut = eventValueFlow<String>()


    override val successPatch = eventValueFlow<String>()
    override val errorPatch = eventValueFlow<String>()
    override val progressPatch = eventValueFlow<String>()


    override fun getCart() {
        if (!isConnected()) {
            return
        }

        categoryRepository.getCart().onEach {
            available.clear()
            unavailable.clear()
            it.onSuccess {
                progressFlowProduct.emit(false)
                progressFlowProductAv.emit(false)
                progressFlowProductUn.emit(false)
                for (i in it.products) {
                    if (i.available == 1) {
                        available.add(i)
                    } else {
                        unavailable.add(i)
                    }
                }
                successFlowCartAvailable.emit(available)
                successFlowCartUnavailable.emit(unavailable)
                successFlowCart.emit(it)
            }
            it.onFailure { throwable ->
                progressFlowProductAv.emit(false)
                progressFlowProduct.emit(false)
                progressFlowProductUn.emit(false)
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
                successFlowDeleteAvailable.emit(Unit)
            }
            it.onFailure { throwable ->
                errorFlowDeleteAvailable.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun deleteAllCart() {
        if (!isConnected()) {
            return
        }
        categoryRepository.deleteAllCart().onEach {
            it.onSuccess {
                successFlowDelete.emit(Unit)
            }
            it.onFailure { throwable ->
                errorFlowDelete.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun putCartMonth(request: CartMonthRequest) {
        if (!isConnected()) {
            return
        }
        categoryRepository.putCartMonth(request).onEach {
            it.onSuccess {
                successPut.emit("success")
            }
            it.onFailure { throwable ->
                errorPut.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun countCart(request: CartParchRequest) {
        if (!isConnected()) {
            return
        }
        categoryRepository.patchCart(request).onEach {
            it.onSuccess {
                successPatch.emit("success")
            }
            it.onFailure { throwable ->
                errorPatch.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}