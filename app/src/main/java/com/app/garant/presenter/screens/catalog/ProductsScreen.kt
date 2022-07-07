package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.response.category.product.Product
import com.app.garant.databinding.ScreenProductsBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.viewModel.catolog.ProductsScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.catalog.ProductsScreenViewModelImpl
import com.app.garant.utils.hideKeyboard
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint

class ProductsScreen : Fragment(R.layout.screen_products) {
    private val bind by viewBinding(ScreenProductsBinding::bind)
    private val args by navArgs<ProductsScreenArgs>()
    private val productAdapter = ProductsAdapter()
    private val viewModel: ProductsScreenViewModel by viewModels<ProductsScreenViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.nameCategory.text = args.name

        bind.progress.bringToFront()

        val idCategory = args.id


        if (idCategory == 1)
            viewModel.getAllProducts(2)
        else
            viewModel.getAllProducts(1)

        view.setOnClickListener {
            it.hideKeyboard()
        }


        viewModel.successFlow.onEach {
            bind.progress.visibility = View.GONE
            productAdapter.submitList(it.data)
            productAdapter.setListenerClick {
                findNavController().navigate(R.id.nav_product_details)
            }
        }.launchIn(lifecycleScope)

        viewModel.progressFlow.onEach {
            bind.progress.visibility = View.VISIBLE
        }.launchIn(lifecycleScope)


        bind.productsRecycler.adapter = productAdapter
        bind.productsRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.action_productsPage_to_emptyFavoritePage)
        }


    }

    companion object {
        const val NAME_CATEGORY = "NAME_CATEGORY"
        const val ID_CATEGORY = "ID_CATEGORY"
    }


}