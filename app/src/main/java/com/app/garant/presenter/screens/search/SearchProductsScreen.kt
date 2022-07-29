package com.app.garant.presenter.screens.search

import android.content.Context
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.databinding.ScreenSearchProductsBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.adapters.search.SearchAdapter
import com.app.garant.presenter.dialogs.DialogFilter
import com.app.garant.presenter.screens.catalog.CategoryScreenDirections
import com.app.garant.presenter.screens.catalog.ProductsScreenDirections
import com.app.garant.presenter.viewModel.search.SearchProductsScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.search.SearchProductsScreenViewModelImpl
import com.app.garant.utils.showToast
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

@AndroidEntryPoint

class SearchProductsScreen : Fragment(R.layout.screen_search_products) {

    private val viewModel: SearchProductsScreenViewModel by viewModels<SearchProductsScreenViewModelImpl>()
    private val bind by viewBinding(ScreenSearchProductsBinding::bind)
    private val args = navArgs<SearchProductsScreenArgs>()
    private val adapterSearch by lazy { SearchAdapter() }
    val adapter by lazy { ProductsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.successSearch.onEach {
            adapterSearch.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        bind.listSearch.adapter = adapterSearch
        bind.listSearch.layoutManager = LinearLayoutManager(requireContext())
        adapterSearch.setListenerClick { query ->
            val action =
                SearchProductsScreenDirections.actionSearchProductsScreenSelf(
                    query
                )
            findNavController().navigate(action)
        }

        bind.listSearch.visibility = View.GONE
        val query = args.value.query
        if (query.isNotBlank())
            viewModel.search(query)
        else {
            findNavController().popBackStack()
        }

        viewModel.successFlowSearch.onEach {
            adapter.submitList(it.data)
            bind.productsRecycler.adapter = adapter

            if (it.data.isEmpty()) {
                bind.searchQuery.text = "По запросу “$query” ничего не найдено"
                bind.nothingFound.visibility = View.VISIBLE
                bind.productsRecycler.visibility = View.GONE
                bind.panel.visibility = View.GONE
            } else {
                bind.productsRecycler.layoutManager =
                    GridLayoutManager(requireContext(), 2)
                bind.searchQuery.text = "По запросу “${query}” найдено ${it.data.size} результатов"
            }

            bind.sort.setOnClickListener {
                val wrapper: Context =
                    ContextThemeWrapper(requireContext(), R.style.Widget_App_PopupMenu)
                val popUpMenu = PopupMenu(wrapper, it)
                popUpMenu.inflate(R.menu.pop_menu)
                popUpMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.cheaper_products_filter -> {
                            StaticValue.filter.value = Unit
                        }
                        R.id.discount_products_filter -> {}
                        R.id.new_products_filter -> {}
                        R.id.expensive_products_filter -> {}
                    }
                    false
                }
                popUpMenu.show()
            }

            adapter.setCartListenerClick { idProduct, index, isChecked ->
                if (isChecked) {
                    viewModel.addCart(CartRequest(1, idProduct))
                    viewModel.successFlowCartRemove.onEach {
                        adapter.notifyItemChanged(idProduct)
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                } else {
                    viewModel.removeCart(CartDeleteRequest(idProduct))
                    viewModel.successFlowCartAdd.onEach {
                        adapter.notifyItemChanged(idProduct)
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                }
            }

            bind.filter.setOnClickListener {
                val dialog = DialogFilter()
                dialog.show(childFragmentManager, "DIALOG_FILTER")
                dialog.setReset {
                    dialog.dismiss()
                }
                dialog.setUse {
                    dialog.dismiss()
                }
            }
            voiceSearch()
            searchList()
            delay(500)
            bind.searchBar.inputType = InputType.TYPE_CLASS_TEXT
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        bind.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

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
                            if (bind.searchBar.text!!.isNotBlank()) {
                                val action =
                                    SearchProductsScreenDirections.actionSearchProductsScreenSelf(
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
                val action: NavDirections =
                    SearchProductsScreenDirections.actionSearchProductsScreenSelf(v.text.toString())
                findNavController().navigate(action)
                true
            } else {
                false
            }
        }
        bind.searchBar.setOnFocusChangeListener { v, hasFocus ->
            bind.listSearch.isVisible = hasFocus
        }


        adapter.setFavoriteListenerClick { idProduct, index, isChecked ->
            if (isChecked) {
                StaticValue.cartAmount.value = Unit
                viewModel.addFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowCartRemove.onEach {
                    adapter.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                StaticValue.cartAmount.value = Unit
                viewModel.removeFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowCartAdd.onEach {
                    adapter.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
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


    private fun searchList() {
        bind.searchBar.doAfterTextChanged {
            bind.listSearch.isVisible = bind.searchBar.text.toString().isNotBlank()
        }
        bind.searchBar.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotBlank()) {
                    viewModel.getSearch(query)
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        bind.searchBar.setText("")
        adapterSearch.submitList(emptyList())
        bind.listSearch.visibility = View.GONE
    }
}