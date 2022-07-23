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
    val successFlowCartAdd: Flow<CartResponse>
    val successFlowCartRemove: Flow<Unit>
    val progressFlowProduct: Flow<Boolean>

    fun getProducts()

    fun addCart(request: CartRequest)
    fun removeCart(request: CartDeleteRequest)

    fun addFavorite(request: FavoriteRequest)
    fun removeFavorite(request: FavoriteRequest)
}