package com.app.garant.presenter.screens.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.databinding.ScreenSearchProductsBinding
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.presenter.dialogs.DialogFilter
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

        }.launchIn(lifecycleScope)

        bind.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}