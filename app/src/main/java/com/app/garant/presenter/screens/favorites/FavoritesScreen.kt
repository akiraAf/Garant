package com.app.garant.presenter.screens.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.models.ProductData
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.databinding.ScreenFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint


class FavoritesScreen : Fragment(R.layout.screen_favorites) {

    private val bind by viewBinding(ScreenFavoritesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var data = ArrayList<ProductData>()

        for (i in 1..10){
            data.add( ProductData("2 300 000 сум", "x 12 мес", "10 700 000 сум",
                "Apple iPhone 12 \n128GB", R.drawable.default_product)
            )
        }
//        val productAdapter by lazy { ProductsAdapter(data, true) }

//        bind.favoriteRV.layoutManager = GridLayoutManager(activity, 2)
//        bind.favoriteRV.adapter = productAdapter

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }
//        productAdapter.setListenerClick {
//            findNavController().navigate(R.id.action_favoritesPage_to_nav_product_details)
//        }
    }
}