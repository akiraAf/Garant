package com.app.garant.presenter.viewModel.viewModelimpl.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.other.StaticValue
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.main.ProdutsPageViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProdutsPageViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel(), ProdutsPageViewModel {

    override val successFlowProduct = eventValueFlow<ArrayList<ProductResponseItem>>()
    override val progressFlowProduct = eventValueFlow<Boolean>()
    override val errorFlowProduct = eventValueFlow<String>()

    override val successFlowCartAdd = eventValueFlow<Unit>()
    override val progressFlowCartAdd = eventValueFlow<Boolean>()
    override val errorFlowCartAdd = eventValueFlow<String>()

    override val successFlowCartRemove = eventValueFlow<Unit>()
    override val progressFlowCartRemove = eventValueFlow<Boolean>()
    override val errorFlowCartRemove = eventValueFlow<String>()

    override val successFlowFavoriteAdd = eventValueFlow<Unit>()
    override val progressFlowFavoriteAdd = eventValueFlow<Boolean>()
    override val errorFlowFavoriteAdd = eventValueFlow<String>()

    override val successFlowFavoriteRemove = eventValueFlow<Unit>()
    override val progressFlowFavoriteRemove = eventValueFlow<Boolean>()
    override val errorFlowFavoriteRemove = eventValueFlow<String>()


    override fun getProducts() {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlowProduct.emit(true)
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
        viewModelScope.launch {
            progressFlowCartAdd.emit(true)
        }
        categoryRepository.addCart(request).onEach {
            it.onSuccess {
                progressFlowCartAdd.emit(false)
                successFlowCartAdd.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowCartAdd.emit(false)
                errorFlowCartAdd.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlowCartRemove.emit(true)
        }

        categoryRepository.deleteCart(request).onEach {
            it.onSuccess {
                progressFlowCartRemove.emit(false)
                successFlowCartRemove.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowCartRemove.emit(false)
                errorFlowCartRemove.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun addFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlowFavoriteAdd.emit(true)
        }

        categoryRepository.addFavorite(request).onEach {
            it.onSuccess {
                progressFlowFavoriteAdd.emit(false)
                successFlowFavoriteAdd.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowFavoriteAdd.emit(false)
                errorFlowFavoriteAdd.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlowFavoriteRemove.emit(true)
        }

        categoryRepository.deleteFavorite(request).onEach {
            it.onSuccess {
                progressFlowFavoriteRemove.emit(false)
                successFlowFavoriteRemove.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowFavoriteRemove.emit(false)
                errorFlowFavoriteRemove.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}