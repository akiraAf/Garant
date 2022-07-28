package com.app.garant.presenter.viewModel.viewModelimpl.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.favorite.FavoriteResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.profile.FavoriteScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : FavoriteScreenViewModel, ViewModel() {

    override val successFlowFavoriteAdd = eventValueFlow<Unit>()
    override val errorFlowFavoriteAdd = eventValueFlow<String>()

    override val errorFlowFavoriteRemove = eventValueFlow<String>()
    override val successFlowFavoriteRemove = eventValueFlow<Unit>()

    override val successFlowCartAdd = eventValueFlow<CartResponse>()
    override val errorFlowCartAdd = eventValueFlow<String>()

    override val successFlowCartRemove = eventValueFlow<Unit>()
    override val errorFlowCartRemove = eventValueFlow<String>()

    override val successFlowFavorite = eventValueFlow<FavoriteResponse>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val errorFlow = eventValueFlow<String>()


    var addFavJOb: Job? = null
    override fun addFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }
        addFavJOb?.cancel()
        addFavJOb = categoryRepository.addFavorite(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowFavoriteAdd.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlowFavoriteAdd.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    var removeFavJOb: Job? = null
    override fun removeFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }
        removeFavJOb?.cancel()
        removeFavJOb = categoryRepository.deleteFavorite(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowFavoriteRemove.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlowFavoriteRemove.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    var favJOb: Job? = null
    override fun getFavorite() {
        if (!isConnected()) {
            return
        }
        favJOb?.cancel()
        favJOb = categoryRepository.getFavorite().onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowFavorite.emit(it)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    var addCartJob: Job? = null
    override fun addCart(request: CartRequest) {
        if (!isConnected()) {
            return
        }
        addCartJob?.cancel()
        addCartJob = categoryRepository.addCart(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowCartAdd.emit(it)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlowCartAdd.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    var removeCartJob: Job? = null
    override fun removeCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }
        removeCartJob?.cancel()
        removeCartJob = categoryRepository.deleteCart(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowCartRemove.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlowCartRemove.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

}
