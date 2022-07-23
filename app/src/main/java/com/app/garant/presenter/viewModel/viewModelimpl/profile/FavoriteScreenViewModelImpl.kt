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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModelImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : FavoriteScreenViewModel, ViewModel() {

    override val successFlowFavoriteAdd = eventValueFlow<Unit>()
    override val successFlowFavoriteRemove = eventValueFlow<Unit>()
    override val successFlowFavorite = eventValueFlow<FavoriteResponse>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val successFlowCartAdd = eventValueFlow<CartResponse>()
    override val successFlowCartRemove = eventValueFlow<Unit>()
    override val errorFlow = eventValueFlow<String>()

    override fun addFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.addFavorite(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowFavoriteAdd.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.deleteFavorite(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowFavoriteAdd.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun getFavorite() {
        if (!isConnected()) {
            return
        }

        categoryRepository.getFavorite().onEach {
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

    override fun addCart(request: CartRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.addCart(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowCartAdd.emit(it)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.deleteCart(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowCartRemove.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

}
