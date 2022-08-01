package com.app.garant.presenter.viewModel.profile

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.EmptyResponse
import com.app.garant.data.response.favorite.FavoriteResponse
import kotlinx.coroutines.flow.Flow

interface FavoriteScreenViewModel {

    val successFlowFavoriteAdd: Flow<Unit>
    val progressFlowFavoriteAdd: Flow<Boolean>
    val errorFlowFavoriteAdd: Flow<String>

    val successFlowFavoriteRemove: Flow<Unit>
    val progressFlowFavoriteRemove: Flow<Boolean>
    val errorFlowFavoriteRemove: Flow<String>

    val successFlowGetFavorite: Flow<FavoriteResponse>
    val progressFlowGetFavorite: Flow<Boolean>
    val errorFlowGetFavorite: Flow<String>

    val successFlowCartAdd: Flow<EmptyResponse>
    val progressFlowCartAdd: Flow<Boolean>
    val errorFlowCartAdd: Flow<String>

    val successFlowCartRemove: Flow<Unit>
    val progressFlowCartRemove: Flow<Boolean>
    val errorFlowCartRemove: Flow<String>

    fun getFavorite()

    fun addCart(request: CartRequest)
    fun removeCart(request: CartDeleteRequest)
    fun addFavorite(request: FavoriteRequest)
    fun removeFavorite(request: FavoriteRequest)

}
