package com.app.garant.presenter.screens.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.ScreenSearchProductsBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.viewModel.search.SearchProductsScreenViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.search.SearchProductsScreenViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint

class SearchProductsScreen : Fragment(R.layout.screen_search_products) {

    private val viewModel: SearchProductsScreenViewModel by viewModels<SearchProductsScreenViewModelImpl>()
    private val bind by viewBinding(ScreenSearchProductsBinding::bind)
    private val args = navArgs<SearchProductsScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = args.value.query
        viewModel.getSearch(query)

        val adapter = ProductsAdapter()

        viewModel.successFlowSearch.onEach {
            adapter.submitList(it.data)
            bind.productsRecycler.adapter = adapter

            bind.productsRecycler.layoutManager =
                GridLayoutManager(requireContext(), 2)
            bind.searchQuery.text = "По запросу “${query}” найдено ${it.data.size} результатов"
        }.launchIn(lifecycleScope)


    }

}