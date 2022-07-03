package com.app.garant.presenter.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ItemRecyclerBinding
import com.app.garant.models.ProductData
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.viewModel.main.PopularViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.main.PopularViewModelImpl
import com.app.garant.utils.isConnected
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint

class PopularPage : Fragment(R.layout.item_recycler) {

    private val bind by viewBinding(ItemRecyclerBinding::bind)
    private val viewModel: PopularViewModel by viewModels<PopularViewModelImpl>()

    private val adapterProduct = ProductsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.successFlow.onEach {
            bind.recycler.adapter = adapterProduct
            adapterProduct.submitList(it)
        }

        if (isConnected())
            viewModel.getProducts()

        bind.recycler.layoutManager = GridLayoutManager(activity, 2)



        adapterProduct.setListenerClick {
            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
        }
    }

}
