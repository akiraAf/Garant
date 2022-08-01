package com.app.garant.presenter.viewModel.catolog

import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.EmptyResponse
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList


interface ProductsScreenViewModel {

    val errorFlow: Flow<String>
    val successFlow: Flow<AllProductsResponse>
    val progressFlow: Flow<Boolean>

    val successFlowSearch: Flow<ArrayList<String>>
    val progressFlowSearch: Flow<Boolean>
    val errorFlowSearch: Flow<String>

    val successFlowCartAdd: Flow<EmptyResponse>
    val progressFlowCartAdd: Flow<Boolean>
    val errorFlowCartAdd: Flow<String>

    val successFlowCartRemove: Flow<Unit>
    val progressFlowCartRemove: Flow<Boolean>
    val errorFlowCartRemove: Flow<String>

    val successFlowFavoriteAdd: Flow<Unit>
    val progressFlowFavoriteAdd: Flow<Boolean>
    val errorFlowFavoriteAdd: Flow<String>

    val successFlowFavoriteRemove: Flow<Unit>
    val progressFlowFavoriteRemove: Flow<Boolean>
    val errorFlowFavoriteRemove: Flow<String>

    fun getAllProducts(id: Int)

    fun search(query: String)
    fun initial(engine: TextToSpeech, launcher: ActivityResultLauncher<Intent>): Job
    fun displaySpeechRecognizer()
    fun speak(text: String): Job

    fun addCart(request: CartRequest)
    fun removeCart(request: CartDeleteRequest)

    fun addFavorite(request: FavoriteRequest)
    fun removeFavorite(request: FavoriteRequest)
}