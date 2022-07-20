package com.app.garant.presenter.viewModel.catolog

import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.activity.result.ActivityResultLauncher
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
    fun getAllProducts(id: Int)
    fun search(query: String)
    fun initial(engine: TextToSpeech, launcher: ActivityResultLauncher<Intent>): Job
    fun displaySpeechRecognizer()
    fun speak(text: String): Job
}