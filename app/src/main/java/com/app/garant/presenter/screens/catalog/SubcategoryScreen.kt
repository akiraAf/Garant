package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.presenter.adapters.category.SubcategoryAdapter
import com.app.garant.databinding.ScreenSubcategoryBinding
import com.app.garant.presenter.viewModel.catolog.SubCategoryViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.catalog.SubCategoryViewModelImpl
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint

class SubcategoryScreen : Fragment(R.layout.screen_subcategory) {

    private val bind by viewBinding(ScreenSubcategoryBinding::bind)
    private val subcategoryAdapter: SubcategoryAdapter = SubcategoryAdapter()
    private val viewModel: SubCategoryViewModel by viewModels<SubCategoryViewModelImpl>()
    private val args by navArgs<SubcategoryScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.subCategory.toList()
        val name = args.nameSubCategory

        bind.nameCategory.text = name

        if (category!!.isNotEmpty())
            subcategoryAdapter.submitList(category)
        else {
            bind.subcategoryRecycler.visibility = View.GONE
        }


        bind.subcategoryRecycler.layoutManager = LinearLayoutManager(activity)
        bind.subcategoryRecycler.adapter = subcategoryAdapter

        subcategoryAdapter.setListenerClick { id, name ->
            val action = SubcategoryScreenDirections.actionSubcategoryPageToProductsPage(name, id)
            findNavController().navigate(action)
        }
        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.action_subcategoryPage_to_emptyFavoritePage)
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        voiceSearch()
    }

    private fun voiceSearch() {
        viewModel.initial(textToSpeechEngine, startForResult)
        with(bind) {
            searchBar.setDrawableClickListener(object : onDrawableClickListener {
                override fun onClick(target: DrawablePosition) {
                    when (target) {
                        DrawablePosition.RIGHT -> {
                            viewModel.displaySpeechRecognizer()
                        }
                        DrawablePosition.LEFT -> {
                            if(bind.searchBar.text!!.isNotEmpty()){
                                val action =
                                    SubcategoryScreenDirections.actionSubcategoryPageToSearchProductsScreen(
                                        bind.searchBar.text!!.toString()
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            })
        }

        bind.searchBar.setOnEditorActionListener { v, actionId, event ->
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
            bind.searchBar.setText(spokenText)
        }
    }


    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) {
            if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("in_ID")
        }
    }

}