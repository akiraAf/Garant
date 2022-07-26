package com.app.garant.presenter.viewModel.viewModelimpl.catalog

import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.response.category.allProducts.AllProductsResponse
import com.app.garant.data.response.category.categories.CategoryResponse
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.presenter.viewModel.catolog.CategoryViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CategoryViewModelImpl @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel(), CategoryViewModel {

    override val successFlow = eventValueFlow<CategoryResponse>()
    override val errorFlow = eventValueFlow<String>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val successSearch = eventValueFlow<ArrayList<String>>()
    private lateinit var textToSpeechEngine: TextToSpeech
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private val search: ArrayList<String> = ArrayList()
    private var dataTemp = CategoryResponse()

    override fun getCategory() {
        if (!isConnected()) {
            return
        }

        categoryRepository.getCategory().onEach {
            it.onSuccess { data ->
                progressFlow.emit(false)
                dataTemp = data
                successFlow.emit(dataTemp)
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
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale(Locale.ENGLISH.language))
            putExtra(RecognizerIntent.EXTRA_PROMPT, Locale(Locale.ENGLISH.language))
        })
    }

    override fun speak(text: String) = viewModelScope.launch {
        textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    var searchJob: Job? = null
    override fun search(query: String) {
        search.clear()
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        searchJob?.cancel()
        if (query.isNotBlank())
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

}