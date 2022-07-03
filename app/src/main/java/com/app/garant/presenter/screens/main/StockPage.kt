package com.app.garant.presenter.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageStockBinding
import com.app.garant.models.ProductData
import com.app.garant.presenter.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class StockPage : Fragment(R.layout.page_stock) {

    private val bind by viewBinding(PageStockBinding::bind)
    private var data = ArrayList<ProductData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        for (i in 1..20) {
            data.add(
                ProductData(
                    "2 300 000 сум", "x 12 мес", "10 700 000 сум",
                    "Apple iPhone 13 \n128GB", R.drawable.watch
                )
            )
        }
//
//        val adapterProduct = ProductsAdapter(data)
//        bind.recycler.layoutManager = GridLayoutManager(activity, 2)
//        bind.recycler.adapter = adapterProduct
//
//        adapterProduct.setListenerClick {
//            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
//        }
    }


}