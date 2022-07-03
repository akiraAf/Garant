package com.app.garant.presenter.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.databinding.PageTopsellingBinding
import com.app.garant.models.ProductData
import com.app.garant.presenter.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class TopSellingPage : Fragment(R.layout.page_topselling) {

    private val bind by viewBinding(PageTopsellingBinding::bind)
    private var data = ArrayList<ProductData>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapterProduct = ProductsAdapter(data)
//        val layoutManager = GridLayoutManager(activity, 2)
//
//        bind.recycler.layoutManager = layoutManager
//        initData()
//        bind.recycler.adapter = adapterProduct
//
//        adapterProduct.setListenerClick {
//            findNavController().navigate(R.id.action_mainPage_to_nav_product_details)
//        }

    }

    private fun initData() {
        for (i in 1..20) {
            if (i % 2 == 1)
                data.add(
                    ProductData(
                        "2 300 000 сум", "x 12 мес", "10 700 000 сум",
                        "Apple iPhone 11 \n128GB", R.drawable.watch
                    )
                )
            else
                data.add(
                    ProductData(
                        "2 300 000 сум", "x 12 мес", "10 700 000 сум",
                        "Apple iPhone 11 \n128GB", R.drawable.product_img
                    )
                )
        }
    }


}

