package com.app.garant.presenter.viewModel.viewModelimpl.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.EmptyResponse
import com.app.garant.data.response.favorite.FavoriteResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.profile.FavoriteScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : FavoriteScreenViewModel, ViewModel() {

    override val successFlowFavoriteAdd = eventValueFlow<Unit>()
    override val errorFlowFavoriteAdd = eventValueFlow<String>()
    override val progressFlowFavoriteAdd = eventValueFlow<Boolean>()

    override val errorFlowFavoriteRemove = eventValueFlow<String>()
    override val successFlowFavoriteRemove = eventValueFlow<Unit>()
    override val progressFlowFavoriteRemove = eventValueFlow<Boolean>()

    override val successFlowCartAdd = eventValueFlow<EmptyResponse>()
    override val progressFlowCartAdd = eventValueFlow<Boolean>()
    override val errorFlowCartAdd = eventValueFlow<String>()


    override val successFlowCartRemove = eventValueFlow<Unit>()
    override val progressFlowCartRemove = eventValueFlow<Boolean>()
    override val errorFlowCartRemove = eventValueFlow<String>()

    override val successFlowGetFavorite = eventValueFlow<FavoriteResponse>()
    override val progressFlowGetFavorite = eventValueFlow<Boolean>()
    override val errorFlowGetFavorite = eventValueFlow<String>()


    var addFavJob: Job? = null
    override fun addFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }
        addFavJob?.cancel()
        addFavJob = viewModelScope.launch {

            progressFlowFavoriteAdd.emit(true)

            categoryRepository.addFavorite(request).collect {
                it.onSuccess {
                    progressFlowFavoriteAdd.emit(false)
                    successFlowFavoriteAdd.emit(Unit)
                }
                it.onFailure { throwable ->
                    progressFlowFavoriteAdd.emit(false)
                    errorFlowFavoriteAdd.emit(throwable.message.toString())
                }
            }
        }
    }

    var removeFavJOb: Job? = null
    override fun removeFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }
        removeFavJOb?.cancel()
        removeFavJOb = viewModelScope.launch {

            progressFlowFavoriteRemove.emit(true)

            categoryRepository.deleteFavorite(request).collect {
                it.onSuccess {
                    progressFlowFavoriteRemove.emit(false)
                    successFlowFavoriteRemove.emit(Unit)
                }
                it.onFailure { throwable ->
                    progressFlowFavoriteRemove.emit(false)
                    errorFlowFavoriteRemove.emit(throwable.message.toString())
                }
            }
        }
    }

    var favJOb: Job? = null
    override fun getFavorite() {
        if (!isConnected()) {
            return
        }
        favJOb?.cancel()
        favJOb = viewModelScope.launch {

            progressFlowGetFavorite.emit(true)

            categoryRepository.getFavorite().collect {
                it.onSuccess {
                    progressFlowGetFavorite.emit(false)
                    successFlowGetFavorite.emit(it)
                }
                it.onFailure { throwable ->
                    progressFlowGetFavorite.emit(false)
                    errorFlowGetFavorite.emit(throwable.message.toString())
                }
            }
        }
    }

    var addCartJob: Job? = null
    override fun addCart(request: CartRequest) {
        if (!isConnected()) {
            return
        }
        addCartJob?.cancel()
        addCartJob = viewModelScope.launch {
            progressFlowCartAdd.emit(true)

            categoryRepository.addCart(request).collect {
                it.onSuccess {
                    progressFlowCartAdd.emit(false)
                    successFlowCartAdd.emit(it)
                }
                it.onFailure { throwable ->
                    progressFlowCartAdd.emit(false)
                    errorFlowCartAdd.emit(throwable.message.toString())
                }
            }
        }
    }

    var removeCartJob: Job? = null
    override fun removeCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }
        removeCartJob?.cancel()
        removeCartJob = viewModelScope.launch {
            progressFlowCartRemove.emit(true)
            categoryRepository.deleteCart(request).collect {
                progressFlowCartRemove.emit(true)
                it.onSuccess {
                    progressFlowCartRemove.emit(false)
                    successFlowCartRemove.emit(Unit)
                }
                it.onFailure { throwable ->
                    progressFlowCartRemove.emit(false)
                    errorFlowCartRemove.emit(throwable.message.toString())
                }
            }
        }
    }

}
