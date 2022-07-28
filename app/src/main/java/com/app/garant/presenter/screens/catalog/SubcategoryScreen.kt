package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.category.SubcategoryAdapter
import com.app.garant.databinding.ScreenSubcategoryBinding
import com.app.garant.presenter.adapters.search.SearchAdapter
import com.app.garant.presenter.viewModel.catolog.SubCategoryViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.catalog.SubCategoryViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

@AndroidEntryPoint

class SubcategoryScreen : Fragment(R.layout.screen_subcategory) {

    private val bind by viewBinding(ScreenSubcategoryBinding::bind)
    private val subcategoryAdapter by lazy { SubcategoryAdapter() }
    private val adapterSearch by lazy { SearchAdapter() }
    private val viewModel: SubCategoryViewModel by viewModels<SubCategoryViewModelImpl>()
    private val args by navArgs<SubcategoryScreenArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.subCategory.toList()
        val name = args.nameSubCategory

        view.setOnClickListener {
            it.hideKeyboard()
            bind.search.clearFocus()
        }

        bind.nameCategory.text = name

        if (category.isNotEmpty())
            subcategoryAdapter.submitList(category)
        else {
            bind.subcategoryRecycler.visibility = View.GONE
        }

        view.setOnClickListener {
            it.hideKeyboard()
        }

        viewModel.successSearch.onEach {
            adapterSearch.submitList(it)
            bind.listSearch.visibility = View.VISIBLE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapterSearch.setListenerClick { data ->
            val action =
                SubcategoryScreenDirections.actionSubcategoryPageToSearchProductsScreen(
                    data
                )
            findNavController().navigate(action)
        }

        bind.listSearch.adapter = adapterSearch
        bind.listSearch.layoutManager = LinearLayoutManager(requireContext())


        bind.subcategoryRecycler.layoutManager = LinearLayoutManager(activity)
        bind.subcategoryRecycler.adapter = subcategoryAdapter

        subcategoryAdapter.setListenerClick { id, name ->
            val action = SubcategoryScreenDirections.actionSubcategoryPageToProductsPage(name, id)
            findNavController().navigate(action)
        }
        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.favoritesScreen)
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        voiceSearch()
        searchList()
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
                            if (bind.search.text!!.isNotEmpty()) {
                                val action =
                                    SubcategoryScreenDirections.actionSubcategoryPageToSearchProductsScreen(
                                        bind.search.text!!.toString()
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            })
        }

        bind.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val action =
                    SubcategoryScreenDirections.actionSubcategoryPageToSearchProductsScreen(v.text!!.toString())
                findNavController().navigate(action)
                true
            } else {
                false
            }
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
            if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("in_ID")
        }
    }

    private fun searchList() {

        bind.search.doAfterTextChanged {
            bind.listSearch.isVisible = bind.search.text.toString().isNotBlank()
        }

        bind.search.setOnFocusChangeListener { v, hasFocus ->
            bind.listSearch.isVisible = hasFocus
        }

        bind.search.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapterSearch.submitList(emptyList())
                val query = s.toString()
                if (query.isNotBlank())
                    viewModel.search(query)
            }
        })
    }


    override fun onStop() {
        super.onStop()
        bind.search.setText("")
        adapterSearch.submitList(emptyList())
    }

}