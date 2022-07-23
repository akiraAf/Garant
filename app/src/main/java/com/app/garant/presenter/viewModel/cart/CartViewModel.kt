package com.app.garant.presenter.viewModel.cart

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.response.cart.CartDeleteResponse
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.category.product.ProductResponseItem
import kotlinx.coroutines.flow.Flow

interface CartViewModel {

    val errorFlowProduct: Flow<String>
    val successFlowCart: Flow<CartResponse>
    val successFlowDelete: Flow<Unit>
    val errorFlowDelete: Flow<String>
    val progressFlowProduct: Flow<Boolean>

    fun getCart()
    fun deleteCart(request: CartDeleteRequest)
    fun deleteAllCart()
}