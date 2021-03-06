package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.InputType
import android.text.Layout
import android.text.TextWatcher
import android.util.Log
import android.view.FocusFinder
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.response.category.categories.CategoryResponse
import com.app.garant.databinding.ScreenCategoryBinding
import com.app.garant.presenter.adapters.category.CategoryAdapter
import com.app.garant.presenter.adapters.search.SearchAdapter
import com.app.garant.presenter.viewModel.catolog.CategoryViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.catalog.CategoryViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.showToast
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CategoryScreen : Fragment(R.layout.screen_category) {

    private val bind by viewBinding(ScreenCategoryBinding::bind)
    private val viewModel: CategoryViewModel by viewModels<CategoryViewModelImpl>()
    private val adapterCategory by lazy { CategoryAdapter() }
    private val adapterSearch by lazy { SearchAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        voiceSearch()
        searchList()
        initObserver()
        initClicks()

        view.setOnClickListener {
            it.hideKeyboard()
        }

        viewModel.getCategory()

        bind.listSearch.adapter = adapterSearch
        bind.listSearch.layoutManager = LinearLayoutManager(requireContext())

        view.setOnClickListener {
            it.hideKeyboard()
        }

        bind.catalogRV.layoutManager = GridLayoutManager(requireContext(), 2)
        bind.catalogRV.adapter = adapterCategory
    }

    private fun initObserver() {
        viewModel.progressFlow.onEach {
            bind.progress.isVisible = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlow.onEach {
            Log.i("LOL", "CategoryScreen Error $it")
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlowS.onEach {
            Log.i("LOL", "CategoryScreen Error $it")
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlowS.onEach {
            adapterSearch.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlow.onEach {
            adapterCategory.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initClicks() {

        bind.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val action =
                    CategoryScreenDirections.actionCatalogPageToSearchProductsScreen(v.text!!.toString())
                findNavController().navigate(action)
                true
            } else {
                true
            }
        }

        adapterCategory.setListenerClick { sub, name ->
            if (sub.isNotEmpty()) {
                val action: NavDirections =
                    CategoryScreenDirections.actionCatalogPageToSubcategoryPage(
                        sub.toTypedArray(), name
                    )
                findNavController().navigate(action)
            } else {
                showToast("Category is empty")
            }
        }

        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.favoritesScreen)
        }

        adapterSearch.setListenerClick { query ->
            val action =
                CategoryScreenDirections.actionCatalogPageToSearchProductsScreen(
                    query
                )
            findNavController().navigate(action)
        }

    }

    private fun voiceSearch() {
        viewModel.initial(textToSpeechEngine, startForResult)
        with(bind) {
            search.setDrawableClickListener(object : onDrawableClickListener {
                override fun onClick(target: DrawablePosition) {
                    when (target) {
                        DrawablePosition.RIGHT -> {
                            viewModel.displaySpeechRecognizer()
                        }
                        DrawablePosition.LEFT -> {
                            if (bind.search.text!!.isNotBlank()) {
                                val action =
                                    CategoryScreenDirections.actionCatalogPageToSearchProductsScreen(
                                        bind.search.text!!.toString()
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            })
        }
    }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val spokenText: String? =
                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    .let { text -> text?.get(0) }
            bind.search.setText(spokenText)
        }
    }

    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) {
            if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language =
                Locale(Locale.ENGLISH.displayLanguage)
        }
    }

    private fun searchList() {
        bind.search.doAfterTextChanged {
            bind.listSearch.isVisible = bind.search.text.toString().isNotBlank()
        }

        bind.search.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                adapterSearch.submitList(emptyList())
                if (query.isNotBlank()) {
                    viewModel.search(query)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        bind.search.setText("")
        adapterSearch.submitList(emptyList())
        bind.listSearch.visibility = View.GONE
        bind.progress.visibility = View.GONE
    }

}