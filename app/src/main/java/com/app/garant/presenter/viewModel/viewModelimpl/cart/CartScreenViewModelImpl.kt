package com.app.garant.presenter.viewModel.viewModelimpl.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartMonthRequest
import com.app.garant.data.response.cart.CartParchRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.cart.Product
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.cart.CartViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel(), CartViewModel {


    override val successFlowGetCart = eventValueFlow<CartResponse>()
    override val errorFlowGetCart = eventValueFlow<String>()
    override val progressFlowGetCart = eventValueFlow<Boolean>()

    override val successFlowCartUnavailable = eventValueFlow<ArrayList<Product>>()
    override val progressFlowCartUnavailable = eventValueFlow<Boolean>()
    override val errorFlowCartUnavailable = eventValueFlow<String>()

    override val successFlowDelete = eventValueFlow<Unit>()
    override val errorFlowDelete = eventValueFlow<String>()
    override val progressFlowDelete = eventValueFlow<Boolean>()

    override val successFlowDeleteAvailable = eventValueFlow<Unit>()
    override val progressFlowDeleteAvailable = eventValueFlow<Boolean>()
    override val errorFlowDeleteAvailable = eventValueFlow<String>()

    override val successFlowCartAvailable = eventValueFlow<ArrayList<Product>>()
    override val progressFlowCartAvailable = eventValueFlow<Boolean>()
    override val errorFlowCartAvailable = eventValueFlow<String>()

    override val successPutCart = eventValueFlow<Unit>()
    override val errorPutCart = eventValueFlow<String>()
    override val progressPutCart = eventValueFlow<Boolean>()

    override val successPatchCart = eventValueFlow<Unit>()
    override val errorPatchCart = eventValueFlow<String>()
    override val progressPatchCart = eventValueFlow<Boolean>()


    private val available = ArrayList<Product>()
    private val unavailable = ArrayList<Product>()


    private var getCartJob: Job? = null
    override fun getCart() {
        getCartJob?.cancel()
        if (!isConnected()) {
            return
        }

        getCartJob = viewModelScope.launch {
            progressFlowGetCart.emit(true)

            delay(500)
            categoryRepository.getCart().collect {
                available.clear()
                unavailable.clear()

                it.onSuccess {
                    progressFlowGetCart.emit(false)
                    progressFlowCartAvailable.emit(false)
                    progressFlowCartUnavailable.emit(false)
                    for (i in it.products) {
                        if (i.available == 1) {
                            available.add(i)
                        } else {
                            unavailable.add(i)
                        }
                    }
                    successFlowCartAvailable.emit(available)
                    successFlowCartUnavailable.emit(unavailable)
                    successFlowGetCart.emit(it)
                }

                it.onFailure { throwable ->
                    progressFlowCartAvailable.emit(false)
                    progressFlowGetCart.emit(false)
                    progressFlowCartUnavailable.emit(false)
                    errorFlowGetCart.emit(throwable.message.toString())
                    errorFlowCartAvailable.emit(throwable.message.toString())
                    errorFlowCartUnavailable.emit(throwable.message.toString())
                }
            }
        }
    }


    private var deleteCartJob: Job? = null
    override fun deleteCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }
        deleteCartJob?.cancel()
        deleteCartJob = viewModelScope.launch {
            categoryRepository.deleteCart(request).collect {
                it.onSuccess {
                    successFlowDeleteAvailable.emit(Unit)
                    progressFlowDeleteAvailable.emit(false)
                }
                it.onFailure { throwable ->
                    errorFlowDeleteAvailable.emit(throwable.message.toString())
                    progressFlowDeleteAvailable.emit(false)
                }
            }
            delay(3500)
            getCart()
        }
    }

    override fun deleteAllCart() {
        if (!isConnected()) {
            return
        }
        categoryRepository.deleteAllCart().onEach {
            it.onSuccess {
                successFlowDelete.emit(Unit)
                progressFlowDelete.emit(false)
            }
            it.onFailure { throwable ->
                errorFlowDelete.emit(throwable.message.toString())
                progressFlowDelete.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    var putCartMonthJob: Job? = null
    override fun putCartMonth(request: CartMonthRequest) {
        if (!isConnected()) {
            return
        }
        putCartMonthJob?.cancel()
        putCartMonthJob = viewModelScope.launch {
            progressPutCart.emit(true)
            categoryRepository.putCartMonth(request).collect {
                it.onSuccess {
                    successPutCart.emit(Unit)
                    progressPutCart.emit(false)
                    delay(3500)
                    getCart()
                }
                it.onFailure { throwable ->
                    errorPutCart.emit(throwable.message.toString())
                    progressPutCart.emit(false)
                }
            }
            progressPutCart.emit(false)
        }
    }

    private var countJob: Job? = null
    override fun countCart(request: CartParchRequest) {
        if (!isConnected()) {
            return
        }
        countJob?.cancel()
        countJob = viewModelScope.launch {
            categoryRepository.patchCart(request).collect {
                it.onSuccess {
                    successPatchCart.emit(Unit)
                    progressPatchCart.emit(false)
                    delay(3500)
                    getCart()
                }
                it.onFailure { throwable ->
                    errorPatchCart.emit(throwable.message.toString())
                    progressPatchCart.emit(false)
                }
            }
        }
    }


}