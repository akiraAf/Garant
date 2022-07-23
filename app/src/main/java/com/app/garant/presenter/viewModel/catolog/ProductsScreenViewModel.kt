package com.app.garant.presenter.viewModel.catolog

import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.utils.eventValueFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList


interface ProductsScreenViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<AllProductsResponse>
    val progressFlow: Flow<Boolean>
    val successSearch: Flow<ArrayList<String>>

    val successFlowCartAdd: Flow<CartResponse>
    val successFlowCartRemove: Flow<Unit>
    val successFlowFavoriteAdd: Flow<Unit>
    val successFlowFavoriteRemove: Flow<Unit>

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