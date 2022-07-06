package com.app.garant.presenter.screens.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.databinding.ScreenFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint


class FavoritesScreen : Fragment(R.layout.screen_favorites) {

    private val bind by viewBinding(ScreenFavoritesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
//        productAdapter.setListenerClick {
//            findNavController().navigate(R.id.action_favoritesPage_to_nav_product_details)
//        }
    }
}