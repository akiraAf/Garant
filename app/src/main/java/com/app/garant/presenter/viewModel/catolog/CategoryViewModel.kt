package com.app.garant.presenter.viewModel.catolog


import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
import com.app.garant.data.response.category.categories.CategoryResponse
import com.app.garant.utils.eventValueFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface CategoryViewModel {
    val errorFlow: Flow<String>
    val successFlow: Flow<CategoryResponse>
    val progressFlow: Flow<Boolean>


    val errorFlowS: Flow<String>
    val progressFlowS: Flow<Boolean>
    val successFlowS: Flow<ArrayList<String>>

    fun getCategory()
    fun initial(engine: TextToSpeech, launcher: ActivityResultLauncher<Intent>): Job
    fun displaySpeechRecognizer()
    fun speak(text: String): Job
    fun search(query: String)
}