package com.app.garant.presenter.viewModel.search

import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.EmptyResponse
import com.app.garant.data.response.category.search.SearchResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList

interface SearchProductsScreenViewModel {

    val successFlowSearch: Flow<SearchResponse>
    val errorFlow: Flow<String>
    val progressFlow: Flow<Boolean>

    val successSearch: Flow<ArrayList<String>>
    val errorSearch: Flow<String>
    val progressSearch: Flow<Boolean>

    val successFlowFavoriteAdd: Flow<Unit>
    val successFlowFavoriteRemove: Flow<Unit>
    val progressFlowFavorite: Flow<Boolean>

    val successFlowCartAdd: Flow<EmptyResponse>
    val successFlowCartRemove: Flow<Unit>

    fun getSearch(name: String)
    fun search(query: String)
    fun initial(engine: TextToSpeech, launcher: ActivityResultLauncher<Intent>): Job
    fun displaySpeechRecognizer()
    fun speak(text: String): Job

    fun addCart(request: CartRequest)
    fun removeCart(request: CartDeleteRequest)

    fun addFavorite(request: FavoriteRequest)
    fun removeFavorite(request: FavoriteRequest)
}