package com.app.garant.presenter.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.data.response.category.Data
import com.app.garant.databinding.PageTopsellingBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.viewModel.main.TopSellingPageViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.main.TopSellingPageViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint

class TopSellingPage : Fragment(R.layout.page_topselling) {

    private val bind by viewBinding(PageTopsellingBinding::bind)
    private val viewModel: TopSellingPageViewModel by viewModels<TopSellingPageViewModelImpl>()
    private val adapterProduct by lazy { ProductsAdapter() }
    private var productData: List<Data>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (productData == null)
            viewModel.getProducts()

        viewModel.successFlowProduct.onEach {
            for (products in it) {
                if (products.name == StaticValue.nameCategory) {
                    productData = products.products
                    adapterProduct.submitList(productData)

                }
            }
            bind.recycler.adapter = adapterProduct
            bind.recycler.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        }.launchIn(lifecycleScope)


        adapterProduct.setListenerClick {
            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
        }
    }


}

