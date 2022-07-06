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
    private val adapterProduct = ProductsAdapter()
    private val bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getProducts()

        viewModel.successFlowProduct.onEach {
            for (products in it) {
                if (products.name == StaticValue.nameCategory)
                    adapterProduct.submitList(products.products)
            }
            bind.recycler.adapter = adapterProduct
        }.launchIn(lifecycleScope)



        bind.recycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)


        adapterProduct.setListenerClick {
            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
        }
    }


}

