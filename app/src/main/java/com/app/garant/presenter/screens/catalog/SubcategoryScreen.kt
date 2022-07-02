package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.category.SubcategoryAdapter
import com.app.garant.databinding.ScreenSubcategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class SubcategoryScreen : Fragment(R.layout.screen_subcategory) {

    private val bind by viewBinding(ScreenSubcategoryBinding::bind)
    private val subcategoryAdapter: SubcategoryAdapter = SubcategoryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        bind.subcategoryRecycler.layoutManager = LinearLayoutManager(activity)
        bind.subcategoryRecycler.adapter = subcategoryAdapter

        subcategoryAdapter.setListenerClick {
            findNavController().navigate(R.id.action_subcategoryPage_to_productsPage)
        }
        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.action_subcategoryPage_to_emptyFavoritePage)
        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}