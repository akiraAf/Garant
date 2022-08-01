package com.app.garant.presenter.viewModel.cart

import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartMonthRequest
import com.app.garant.data.response.cart.CartParchRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.cart.Product
import kotlinx.coroutines.flow.Flow

interface CartViewModel {


    val successFlowDeleteAvailable: Flow<Unit>
    val progressFlowDeleteAvailable: Flow<Boolean>
    val errorFlowDeleteAvailable: Flow<String>

    val successFlowGetCart: Flow<CartResponse>
    val errorFlowGetCart: Flow<String>
    val progressFlowGetCart: Flow<Boolean>

    val successFlowCartAvailable: Flow<ArrayList<Product>>
    val progressFlowCartAvailable: Flow<Boolean>
    val errorFlowCartAvailable: Flow<String>

    val successFlowCartUnavailable: Flow<ArrayList<Product>>
    val progressFlowCartUnavailable: Flow<Boolean>
    val errorFlowCartUnavailable: Flow<String>

    val successPutCart: Flow<Unit>
    val errorPutCart: Flow<String>
    val progressPutCart: Flow<Boolean>

    val successPatchCart: Flow<Unit>
    val errorPatchCart: Flow<String>
    val progressPatchCart: Flow<Boolean>

    val progressFlowDelete: Flow<Boolean>
    val successFlowDelete: Flow<Unit>
    val errorFlowDelete: Flow<String>

    fun getCart()
    fun deleteCart(request: CartDeleteRequest)
    fun deleteAllCart()
    fun putCartMonth(request: CartMonthRequest)
    fun countCart(request: CartParchRequest)
}