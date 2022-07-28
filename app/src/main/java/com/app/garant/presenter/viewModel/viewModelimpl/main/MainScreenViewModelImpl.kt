package com.app.garant.presenter.viewModel.viewModelimpl.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.category.allProducts.AllProductsResponse
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
    override val tabСontentLoad = eventValueFlow<ArrayList<String>>()
    override val successSearch = eventValueFlow<ArrayList<String>>()
    override val progressFlow = eventValueFlow<Boolean>()
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
        tabСontentLoad.tryEmit(categoryRepository.collectCompanions())
    }

    override fun cancelProcess() {
        searchJob?.cancel()
    }

}