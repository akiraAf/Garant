package com.app.garant.presenter.screens.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.response.category.product.ProductResponseItem
import com.app.garant.databinding.ItemRecyclerBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.viewModel.main.PopularPageViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.main.PopularPageViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint

class PopularPage : Fragment(R.layout.item_recycler) {

    private val bind by viewBinding(ItemRecyclerBinding::bind)
    private val viewModel: PopularPageViewModel by viewModels<PopularPageViewModelImpl>()
    private val adapterProduct = ProductsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getProducts()

        viewModel.successFlowProduct.onEach {
            adapterProduct.submitList(it)
            showToast(it.size.toString())
        }.launchIn(lifecycleScope)

        bind.recycler.adapter = adapterProduct
        bind.recycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)


        adapterProduct.setListenerClick {
            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
        }
    }

}
