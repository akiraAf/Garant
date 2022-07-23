package com.app.garant.presenter.screens.catalog

import android.content.Context
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.data.request.cart.CartDeleteRequest
import com.app.garant.data.request.cart.CartRequest
import com.app.garant.data.request.favorite.FavoriteRequest
import com.app.garant.data.response.category.Data
import com.app.garant.databinding.ScreenProductsBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.dialogs.DialogFilter
import com.app.garant.presenter.viewModel.catolog.ProductsScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.catalog.ProductsScreenViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*


@AndroidEntryPoint

class ProductsScreen : Fragment(R.layout.screen_products) {
    private val bind by viewBinding(ScreenProductsBinding::bind)
    private val args by navArgs<ProductsScreenArgs>()
    private val productAdapter by lazy { ProductsAdapter() }
    private var productsData: List<Data>? = null
    var listAdapter: ArrayAdapter<String>? = null
    private val viewModel: ProductsScreenViewModel by viewModels<ProductsScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.nameCategory.text = args.name
        val idCategory = args.id
        bind.progress.bringToFront()

        if (productsData == null)
            viewModel.getAllProducts(idCategory)
        view.setOnClickListener {
            it.hideKeyboard()
        }

        activity?.onBackPressed()

        viewModel.successFlow.onEach {
            delay(1000)
            bind.progress.visibility = View.GONE
            productsData = it.data
            productAdapter.submitList(productsData)
            productAdapter.setListenerClick {
                findNavController().navigate(R.id.nav_product_details)
            }
        }.launchIn(lifecycleScope)

        productAdapter.setCartListenerClick { idProduct, index, isChecked ->
            if (isChecked) {
                viewModel.addCart(CartRequest(1, idProduct))
                viewModel.successFlowCartRemove.onEach {
                    productAdapter.notifyItemChanged(idProduct)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeCart(CartDeleteRequest(idProduct))
                viewModel.successFlowCartAdd.onEach {
                    productAdapter.notifyItemChanged(idProduct)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }

        productAdapter.setFavoriteListenerClick { idProduct, index, isChecked ->
            if (isChecked) {
                viewModel.addFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowCartRemove.onEach {
                    productAdapter.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeFavorite(FavoriteRequest(idProduct))
                viewModel.successFlowCartAdd.onEach {
                    productAdapter.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }


        viewModel.progressFlow.onEach {
            bind.progress.visibility = View.VISIBLE
        }.launchIn(lifecycleScope)


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

        bind.productsRecycler.adapter = productAdapter
        bind.productsRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.emptyFavoriteScreen)
        }
        view.setOnClickListener {
            it.hideKeyboard()
        }
        voiceSearch()
        searchList()
    }

    companion object {
        const val NAME_CATEGORY = "NAME_CATEGORY"
        const val ID_CATEGORY = "ID_CATEGORY"
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
                                    ProductsScreenDirections.actionProductsScreenToSearchProductsPage(
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
                    ProductsScreenDirections.actionProductsScreenToSearchProductsPage(v.text!!.toString())
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
        bind.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s!!.isEmpty())
                    bind.listSearch.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.isEmpty())
                    bind.listSearch.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (count == 0) {
                    bind.listSearch.visibility = View.GONE
                } else {
                    viewModel.search(query)
                    viewModel.successSearch.onEach {
                        bind.listSearch.visibility = View.VISIBLE
                        listAdapter = ArrayAdapter<String>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            it
                        )
                        bind.listSearch.adapter = listAdapter
                        listAdapter!!.filter.filter(query)
                        bind.listSearch.setOnItemClickListener { parent, view, position, id ->
                            val action =
                                ProductsScreenDirections.actionProductsScreenToSearchProductsPage(
                                    query
                                )
                            findNavController().navigate(action)
                        }
                    }.launchIn(lifecycleScope)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        bind.search.setText("")
        bind.listSearch.visibility = View.GONE
    }

}