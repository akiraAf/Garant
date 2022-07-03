package com.app.garant.presenter.screens.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.models.ProductData
import com.app.garant.presenter.adapters.ProductsAdapter
import com.app.garant.databinding.ScreenProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ProductsScreen : Fragment(R.layout.screen_products) {
    private val bind by viewBinding(ScreenProductsBinding::bind)
    private var data = ArrayList<ProductData>()
//    private val productsAdapter = ProductsAdapter(data)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        for (i in 1..20){
            data.add( ProductData("2 300 000 сум", "x 12 мес", "10 700 000 сум",
                "Apple iPhone 12 \n128GB", R.drawable.default_product)
            )
        }

//
//        bind.productsRecycler.layoutManager = GridLayoutManager(activity,2)
//        bind.productsRecycler.adapter = productsAdapter
//
//        productsAdapter.setListenerClick {
//            findNavController().navigate(R.id.action_productsPage_to_nav_product_details)
//        }

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.favorites.setOnClickListener {
            findNavController().navigate(R.id.action_productsPage_to_emptyFavoritePage)
        }
    }


}