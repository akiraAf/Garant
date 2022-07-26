package com.app.garant.presenter.viewModel.viewModelimpl.search

import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.CartResponse
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.product.ProductResponse
import com.app.garant.data.response.category.search.SearchResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.search.SearchProductsScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchProductsScreenViewModelImpl @Inject constructor(private val categoryRepository: CategoryRepository) :
    SearchProductsScreenViewModel, ViewModel() {

    override val errorFlow = eventValueFlow<String>()
    override val successFlowSearch = eventValueFlow<SearchResponse>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val progressFlowFavorite = eventValueFlow<Boolean>()


    override val successFlowCartAdd = eventValueFlow<CartResponse>()
    override val successFlowCartRemove = eventValueFlow<Unit>()

    override val successFlowFavoriteAdd = eventValueFlow<Unit>()
    override val successFlowFavoriteRemove = eventValueFlow<Unit>()

    override val successSearch = eventValueFlow<ArrayList<String>>()
    private lateinit var textToSpeechEngine: TextToSpeech
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private val search: ArrayList<String> = ArrayList()


    var searchJob: Job? = null
    override fun getSearch(query: String) {
        search.clear()
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        searchJob?.cancel()
        searchJob = categoryRepository.getSearch(query).onEach {
            delay(500)
            it.onSuccess { products ->
                progressFlow.emit(false)
                products.data.map { search.add(it.name) }
                successSearch.emit(search)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun search(query: String) {
        search.clear()
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        categoryRepository.getSearch(query).onEach {
            it.onSuccess { products ->
                progressFlow.emit(false)
                products.data.map { search.add(it.name) }
                successFlowSearch.emit(products)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun initial(
        engine: TextToSpeech, launcher: ActivityResultLauncher<Intent>
    ) = viewModelScope.launch {
        textToSpeechEngine = engine
        startForResult = launcher
    }

    override fun displaySpeechRecognizer() {
        startForResult.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("in_ID"))
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale("Камила милашка"))
        })
    }

    override fun speak(text: String) = viewModelScope.launch {
        textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun addCart(request: CartRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.addCart(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowCartAdd.emit(it)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }
        categoryRepository.deleteCart(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowCartRemove.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun addFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.addFavorite(request).onEach {
            it.onSuccess {
                progressFlowFavorite.emit(false)
                successFlowFavoriteAdd.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowFavorite.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.deleteFavorite(request).onEach {
            it.onSuccess {
                progressFlowFavorite.emit(false)
                successFlowFavoriteAdd.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowFavorite.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }
}