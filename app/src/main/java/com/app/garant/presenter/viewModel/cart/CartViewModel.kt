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

interface CartViewModel {


    val successFlowCartUnavailable: Flow<ArrayList<Product>>
    val successFlowCartAvailable: Flow<ArrayList<Product>>
    val successFlowDeleteAvailable: Flow<Unit>
    val errorFlowDeleteAvailable: Flow<String>

    val successFlowCart: Flow<CartResponse>
    val errorFlowProduct: Flow<String>
    val progressFlowProduct: Flow<Boolean>

    val progressFlowProductAv: Flow<Boolean>
    val progressFlowProductUn: Flow<Boolean>

    val successPut: Flow<String>
    val errorPut: Flow<String>
    val progressPut: Flow<String>

    val successPatch: Flow<String>
    val errorPatch: Flow<String>
    val progressPatch: Flow<String>

    val progressFlowDelete: Flow<Boolean>
    val successFlowDelete: Flow<Unit>
    val errorFlowDelete: Flow<String>

    fun getCart()
    fun deleteCart(request: CartDeleteRequest)
    fun deleteAllCart()
    fun putCartMonth(request: CartMonthRequest)
    fun countCart(request: CartParchRequest)
}