package com.app.garant.presenter.viewModel.viewModelimpl.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.other.StaticValue
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.main.ProdutsPageViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProdutsPageViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel(), ProdutsPageViewModel {

    override val successFlowProduct = eventValueFlow<ArrayList<ProductResponseItem>>()
    override val successFlowCartAdd = eventValueFlow<String>()
    override val successFlowCartRemove = eventValueFlow<String>()
    override val progressFlowProduct = eventValueFlow<Boolean>()
    override val errorFlowProduct = eventValueFlow<String>()

    override val successFlowS = eventValueFlow<Unit>()
    override val progressFlowS = eventValueFlow<Boolean>()
    override val errorFlowS = eventValueFlow<String>()

    override val successFlowR = eventValueFlow<Unit>()
    override val progressFlowR = eventValueFlow<Boolean>()
    override val errorFlowR = eventValueFlow<String>()

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

    override fun addCart(request: CartRequest) {
        if (!isConnected()) {
            return
        }
        categoryRepository.addCart(request).onEach {
            it.onSuccess {
                progressFlowS.emit(false)
                successFlowS.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowS.emit(false)
                errorFlowS.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.deleteCart(request).onEach {
            it.onSuccess {
                progressFlowR.emit(false)
                successFlowR.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowR.emit(false)
                errorFlowR.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun addFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.addFavorite(request).onEach {
            it.onSuccess {
                Log.i("LOL", it.toString())
                progressFlowProduct.emit(false)
                successFlowCartAdd.emit(it.toString())
            }
            it.onFailure { throwable ->
                progressFlowProduct.emit(false)
                errorFlowProduct.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.deleteFavorite(request).onEach {
            it.onSuccess {
                progressFlowProduct.emit(false)
                successFlowCartRemove.emit(it.toString())
            }
            it.onFailure { throwable ->
                progressFlowProduct.emit(false)
                errorFlowProduct.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}