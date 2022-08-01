package com.app.garant.presenter.viewModel.viewModelimpl.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.EmptyResponse
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.main.MainScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel(), MainScreenViewModel {

    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<ProductResponse>()
    override val progressFlow = eventValueFlow<Boolean>()

    override val successFlowCartAdd = eventValueFlow<EmptyResponse>()
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

    override val tabsFlow = eventValueFlow<ArrayList<String>>()

    override val successSearch = eventValueFlow<ArrayList<String>>()

    private val search: ArrayList<String> = ArrayList()

    private var dataTemp = ProductResponse()


    override fun getProducts() {
        if (!isConnected()) {
            return
        }

        categoryRepository.getProducts().onEach {
            it.onSuccess { products ->
                progressFlow.emit(false)
                dataTemp = products
                successFlow.emit(products)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    var searchJob: Job? = null
    override fun getSearch(query: String) {
        searchJob?.cancel()
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }

        searchJob = viewModelScope.launch {
            delay(500)
            categoryRepository.getSearch(query).collect {
                search.clear()
                it.onSuccess { products ->
                    progressFlow.emit(false)
                    products.data.map { search.add(it.name) }
                    successSearch.emit(search)
                }
                it.onFailure { throwable ->
                    progressFlow.emit(false)
                    errorFlow.emit(throwable.message.toString())
                }
            }
        }
    }

    override fun getNames() {
        tabsFlow.tryEmit(categoryRepository.collectCompanions())
    }

    override fun addCart(request: CartRequest) {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlowCartAdd.emit(true)
            categoryRepository.addCart(request).collect {
                progressFlowCartAdd.emit(false)
                it.onSuccess {
                    successFlowCartAdd.emit(it)
                }

                it.onFailure { throwable ->
                    errorFlowCartAdd.emit(throwable.message.toString())
                }
            }

        }
//        categoryRepository.addCart(request).onEach {
//            it.onSuccess {
//                progressFlowCartAdd.emit(false)
//                successFlowCartAdd.emit(Unit)
//            }
//            it.onFailure { throwable ->
//                progressFlowCartAdd.emit(false)
//                errorFlowCartAdd.emit(throwable.message.toString())
//            }
//        }.launchIn(viewModelScope)
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


    override fun cancelProcess() {
        searchJob?.cancel()
    }
}