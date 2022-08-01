package com.app.garant.presenter.viewModel.main

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.EmptyResponse
import com.app.garant.data.response.category.product.ProductResponse
import kotlinx.coroutines.flow.Flow

interface MainScreenViewModel {

    val errorFlow: Flow<String>
    val successFlow: Flow<ProductResponse>
    val progressFlow: Flow<Boolean>

    val successFlowCartAdd: Flow<EmptyResponse>
    val progressFlowCartAdd: Flow<Boolean>
    val errorFlowCartAdd: Flow<String>

    val progressFlowCartRemove: Flow<Boolean>
    val successFlowCartRemove: Flow<Unit>
    val errorFlowCartRemove: Flow<String>

    val errorFlowFavoriteRemove: Flow<String>
    val successFlowFavoriteRemove: Flow<Unit>
    val progressFlowFavoriteRemove: Flow<Boolean>

    val errorFlowFavoriteAdd: Flow<String>
    val successFlowFavoriteAdd: Flow<Unit>
    val progressFlowFavoriteAdd: Flow<Boolean>

    val tabsFlow: Flow<ArrayList<String>>

    val successSearch: Flow<ArrayList<String>>

    fun getProducts()
    fun getSearch(query: String)
    fun getNames()
    fun cancelProcess()

    fun addCart(request: CartRequest)
    fun removeCart(request: CartDeleteRequest)

    fun addFavorite(request: FavoriteRequest)
    fun removeFavorite(request: FavoriteRequest)
}