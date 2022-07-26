package com.app.garant.presenter.viewModel.main

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import kotlinx.coroutines.flow.Flow

interface ProdutsPageViewModel {

    val errorFlowProduct: Flow<String>
    val successFlowProduct: Flow<ArrayList<ProductResponseItem>>
    val successFlowCartAdd: Flow<String>
    val successFlowCartRemove: Flow<String>
    val progressFlowProduct: Flow<Boolean>

    val errorFlowS: Flow<String>
    val successFlowS: Flow<Unit>
    val progressFlowS: Flow<Boolean>

    val errorFlowR: Flow<String>
    val successFlowR: Flow<Unit>
    val progressFlowR: Flow<Boolean>


    fun getProducts()

    fun addCart(request: CartRequest)
    fun removeCart(request: CartDeleteRequest)

    fun addFavorite(request: FavoriteRequest)
    fun removeFavorite(request: FavoriteRequest)
}