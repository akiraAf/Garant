package com.app.garant.presenter.viewModel.profile

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.favorite.FavoriteResponse
import kotlinx.coroutines.flow.Flow

interface FavoriteScreenViewModel {

    val successFlowFavoriteAdd: Flow<Unit>
    val errorFlowFavoriteAdd: Flow<String>

    val successFlowFavoriteRemove: Flow<Unit>
    val errorFlowFavoriteRemove: Flow<String>

    val successFlowFavorite: Flow<FavoriteResponse>

    val successFlowCartAdd: Flow<CartResponse>
    val errorFlowCartAdd: Flow<String>

    val successFlowCartRemove: Flow<Unit>
    val errorFlowCartRemove: Flow<String>

    val errorFlow: Flow<String>
    val progressFlow: Flow<Boolean>

    fun addFavorite(request: FavoriteRequest)
    fun removeFavorite(request: FavoriteRequest)


    fun addCart(request: CartRequest)
    fun removeCart(request: CartDeleteRequest)


    fun getFavorite()
}
