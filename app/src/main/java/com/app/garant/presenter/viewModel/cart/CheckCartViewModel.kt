package com.app.garant.presenter.viewModel.cart

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartMonthRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.response.cart.CartDeleteResponse
import com.app.garant.data.response.cart.CartParchRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.cart.Product
import com.app.garant.data.response.category.product.ProductResponseItem
import kotlinx.coroutines.flow.Flow

interface CheckCartViewModel {


    val successFlow: Flow<CartResponse>
    val errorFlow: Flow<String>
    val progressFlow: Flow<Boolean>

    val successFlowAv: Flow<ArrayList<Product>>
    val errorFlowAv: Flow<String>
    val progressFlowAv: Flow<Boolean>


    fun getCart()
}