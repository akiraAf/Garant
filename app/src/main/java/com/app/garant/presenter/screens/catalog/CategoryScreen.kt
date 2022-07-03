package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.category.CategoryAdapter
import com.app.garant.databinding.ScreenCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class CategoryScreen : Fragment(R.layout.screen_category) {

    private val bind by viewBinding(ScreenCategoryBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterProducts = CategoryAdapter()
        val productLayoutManager = GridLayoutManager(activity, 2)

        bind.catalogRV.layoutManager = productLayoutManager
        bind.catalogRV.adapter = adapterProducts

        adapterProducts.setListenerClick {
            findNavController().navigate(R.id.action_catalogPage_to_subcategoryPage)
        }

        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.action_catalogPage_to_favoritesPage2)
        }
    }

}