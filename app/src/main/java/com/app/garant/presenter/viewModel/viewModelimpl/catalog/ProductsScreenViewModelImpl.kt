package com.app.garant.presenter.viewModel.viewModelimpl.catalog

import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.cart.EmptyResponse
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.catolog.ProductsScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModelImpl @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel(), ProductsScreenViewModel {

    override val successFlow = eventValueFlow<AllProductsResponse>()
    override val errorFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()

    override val progressFlowCartAdd = eventValueFlow<Boolean>()
    override val errorFlowCartAdd = eventValueFlow<String>()
    override val progressFlowFavoriteAdd = eventValueFlow<Boolean>()

    override val successFlowCartAdd = eventValueFlow<EmptyResponse>()
    override val errorFlowFavoriteAdd = eventValueFlow<String>()
    override val successFlowFavoriteAdd = eventValueFlow<Unit>()

    override val progressFlowSearch = eventValueFlow<Boolean>()
    override val errorFlowSearch = eventValueFlow<String>()
    override val successFlowSearch = eventValueFlow<ArrayList<String>>()

    override val successFlowCartRemove = eventValueFlow<Unit>()
    override val progressFlowCartRemove = eventValueFlow<Boolean>()
    override val errorFlowCartRemove = eventValueFlow<String>()

    override val successFlowFavoriteRemove = eventValueFlow<Unit>()
    override val progressFlowFavoriteRemove = eventValueFlow<Boolean>()
    override val errorFlowFavoriteRemove = eventValueFlow<String>()

    private lateinit var textToSpeechEngine: TextToSpeech
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private val search: ArrayList<String> = ArrayList()

    override fun getAllProducts(id: Int) {

        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlow.emit(true)
        }

        categoryRepository.getAllProducts(id).onEach {
            it.onSuccess { data ->
                progressFlow.emit(false)
                successFlow.emit(data)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    private var searchJob: Job? = null
    override fun search(query: String) {
        search.clear()
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlowSearch.emit(true)
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            categoryRepository.getSearch(query).collect {
                delay(500)
                it.onSuccess { products ->
                    products.data.map { search.add(it.name) }
                    progressFlowSearch.emit(false)
                    successFlowSearch.emit(search)
                }
                it.onFailure { throwable ->
                    progressFlowSearch.emit(false)
                    errorFlowSearch.emit(throwable.message.toString())
                }
            }
        }
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
                progressFlowCartAdd.emit(false)
                successFlowCartAdd.emit(it)
            }
            it.onFailure { throwable ->
                progressFlowCartAdd.emit(false)
                errorFlowCartAdd.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeCart(request: CartDeleteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.deleteCart(request).onEach {
            it.onSuccess {
                progressFlowCartRemove.emit(false)
                successFlowCartRemove.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowCartRemove.emit(false)
                errorFlowCartRemove.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun addFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }
        categoryRepository.addFavorite(request).onEach {
            it.onSuccess {
                progressFlowFavoriteAdd.emit(false)
                successFlowFavoriteAdd.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowFavoriteAdd.emit(false)
                errorFlowFavoriteAdd.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun removeFavorite(request: FavoriteRequest) {
        if (!isConnected()) {
            return
        }

        categoryRepository.deleteFavorite(request).onEach {
            it.onSuccess {
                progressFlowFavoriteRemove.emit(false)
                successFlowFavoriteRemove.emit(Unit)
            }
            it.onFailure { throwable ->
                progressFlowFavoriteRemove.emit(false)
                errorFlowFavoriteRemove.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}