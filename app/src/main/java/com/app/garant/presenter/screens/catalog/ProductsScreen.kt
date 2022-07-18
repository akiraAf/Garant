package com.app.garant.presenter.screens.catalog

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
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
import com.app.garant.data.response.category.Data
import com.app.garant.databinding.ScreenProductsBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.dialogs.DialogFilter
import com.app.garant.presenter.viewModel.catolog.ProductsScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.catalog.ProductsScreenViewModelImpl
import com.app.garant.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint

class ProductsScreen : Fragment(R.layout.screen_products) {
    private val bind by viewBinding(ScreenProductsBinding::bind)
    private val args by navArgs<ProductsScreenArgs>()
    private val productAdapter = ProductsAdapter()
    private var productsData: List<Data>? = null
    private val viewModel: ProductsScreenViewModel by viewModels<ProductsScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.nameCategory.text = args.name
        val idCategory = args.id
        bind.progress.bringToFront()

        if (productsData == null)
            viewModel.getAllProducts(idCategory)


        activity?.onBackPressed()

        viewModel.successFlow.onEach {
            bind.progress.visibility = View.GONE
            productsData = it.data
            productAdapter.submitList(productsData)
            productAdapter.setListenerClick {
                findNavController().navigate(R.id.nav_product_details)
            }
        }.launchIn(lifecycleScope)



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

    }

    companion object {
        const val NAME_CATEGORY = "NAME_CATEGORY"
        const val ID_CATEGORY = "ID_CATEGORY"
    }


}