package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.response.category.categories.Category
import com.app.garant.databinding.ScreenCategoryBinding
import com.app.garant.presenter.adapters.category.CategoryAdapter
import com.app.garant.presenter.viewModel.catolog.CategoryViewModel
import com.app.garant.presenter.viewModel.viewModelimpl.catalog.CategoryViewModelImpl
import com.app.garant.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint

class CategoryScreen : Fragment(R.layout.screen_category) {

    private val bind by viewBinding(ScreenCategoryBinding::bind)
    private val viewModel: CategoryViewModel by viewModels<CategoryViewModelImpl>()
    private val bundle = Bundle()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterCategory = CategoryAdapter()
        val productLayoutManager = GridLayoutManager(requireContext(), 2)


        viewModel.getCategory()

        viewModel.progressFlow.onEach {
            bind.progress.bringToFront()
            bind.catalogRV.visibility = View.GONE
            bind.progress.visibility = View.VISIBLE
        }

        viewModel.successFlow.onEach {
            bind.progress.visibility = View.GONE
            bind.catalogRV.visibility = View.VISIBLE
            adapterCategory.submitList(it.reversed())
        }.launchIn(lifecycleScope)

        viewModel.errorFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        bind.catalogRV.layoutManager = productLayoutManager
        bind.catalogRV.adapter = adapterCategory

        adapterCategory.setListenerClick { sub, name ->
            val action: NavDirections = CategoryScreenDirections.actionCatalogPageToSubcategoryPage(
                sub.toTypedArray(), name
            )
            findNavController().navigate(action)
        }

        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.action_catalogPage_to_favoritesPage2)
        }
    }

    companion object {
        const val SUB_CATEGORY = "SUB_CATEGORY"
    }
}