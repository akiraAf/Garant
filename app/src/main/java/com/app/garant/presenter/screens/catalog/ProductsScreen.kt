package com.app.garant.presenter.screens.catalog

import android.content.Context
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.app.garant.databinding.ScreenProductsBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.adapters.search.SearchAdapter
import com.app.garant.presenter.dialogs.DialogFilter
import com.app.garant.presenter.viewModel.catolog.ProductsScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.catalog.ProductsScreenViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.showToast
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*


@AndroidEntryPoint
class ProductsScreen : Fragment(R.layout.screen_products) {

    private val bind by viewBinding(ScreenProductsBinding::bind)
    private val args by navArgs<ProductsScreenArgs>()
    private val productAdapter by lazy { ProductsAdapter() }
    private val adapterSearch by lazy { SearchAdapter() }
    private val viewModel: ProductsScreenViewModel by viewModels<ProductsScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.nameCategory.text = args.name

        val idCategory = args.id + 1
        if (idCategory <= 2)
            viewModel.getAllProducts(idCategory)
        else
            viewModel.getAllProducts(2)

        initObserver()
        initClicks()
        voiceSearch()
        searchList()
        initPanel()


        view.setOnClickListener {
            it.hideKeyboard()
        }

        bind.listSearch.adapter = adapterSearch
        bind.listSearch.layoutManager = LinearLayoutManager(requireContext())

        bind.productsRecycler.adapter = productAdapter
        bind.productsRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
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
                                    ProductsScreenDirections.actionProductsScreenToSearchProductsScreen(
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
            if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("in_ID")
        }
    }


    private fun initObserver() {

        viewModel.progressFlow.onEach {
            bind.progress.isVisible = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlowSearch.onEach {
            adapterSearch.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.successFlow.onEach {
            productAdapter.submitList(it.data)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        productAdapter.setFavoriteListenerClick { product, index, isChecked ->
            if (isChecked) {
                StaticValue.cartAmount.value = Unit
                viewModel.addFavorite(FavoriteRequest(product.id))
                viewModel.successFlowCartRemove.onEach {
                    productAdapter.notifyItemChanged(index)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                StaticValue.cartAmount.value = Unit
                viewModel.removeFavorite(FavoriteRequest(product.id))
                viewModel.successFlowCartAdd.onEach {
                    productAdapter.notifyItemChanged(index)
                    bind.progress.visibility = View.GONE
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
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

    }


    private fun initClicks() {

        bind.search.setOnFocusChangeListener { v, hasFocus ->
            bind.listSearch.isVisible = hasFocus
        }

        bind.search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val action =
                    ProductsScreenDirections.actionProductsScreenToSearchProductsScreen(v.text!!.toString())
                findNavController().navigate(action)
                true
            } else {
                true
            }
        }

        adapterSearch.setListenerClick { query ->
            val action =
                ProductsScreenDirections.actionProductsScreenToSearchProductsScreen(
                    query
                )
            findNavController().navigate(action)
        }

        productAdapter.setListenerClick {
            findNavController().navigate(R.id.action_productsPage_to_nav_product_details)
        }


        productAdapter.setListenerClick {
            findNavController().navigate(R.id.nav_product_details)
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.favoritesScreen)
        }

        productAdapter.setCartListenerClick { product, index, isChecked ->
            if (isChecked) {
                viewModel.addCart(CartRequest(1, product.id))
                viewModel.successFlowCartRemove.onEach {
                    productAdapter.notifyItemRemoved(product.id)
                    StaticValue.cartAmount.value = Unit
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            } else {
                viewModel.removeCart(CartDeleteRequest(product.id))
                viewModel.successFlowCartAdd.onEach {
                    productAdapter.notifyItemRemoved(product.id)
                    StaticValue.cartAmount.value = Unit
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
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
                if (query.isNotBlank())
                    viewModel.search(query)
            }
        })
    }


    private fun initPanel() {

        var click: Boolean = true

        bind.sort.setOnClickListener {
            val wrapper: Context =
                ContextThemeWrapper(requireContext(), R.style.Widget_App_PopupMenu)
            val popUpMenu = PopupMenu(wrapper, it)
            popUpMenu.inflate(R.menu.pop_menu)

            popUpMenu.setOnMenuItemClickListener { menu ->
                    when (menu.itemId) {
                    R.id.cheaper_products_filter -> {
                    }
                    R.id.discount_products_filter -> {
                        click = if (click) {
                            productAdapter.notifyDataSetChanged()
                            viewModel.filterDiscountPercentage(args.id + 1, 1)
                            false
                        } else {
                            productAdapter.notifyDataSetChanged()
                            viewModel.filterDiscountPercentage(args.id + 1, 0)
                            true
                        }
                    }
                    R.id.new_products_filter -> {

                    }
                    R.id.expensive_products_filter -> {

                    }
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
    }


    override fun onStop() {
        super.onStop()
        bind.search.setText("")
        adapterSearch.submitList(emptyList())
        bind.listSearch.visibility = View.GONE
        bind.progress.visibility = View.GONE
    }


}